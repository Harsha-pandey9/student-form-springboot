package com.example.service;

import com.example.config.KafkaConfig;
import com.example.event.StudentEvent;
import com.example.model.Message;
import com.example.student.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Comprehensive Messaging Service
 * Handles both general messaging and student event publishing
 * Combines MessageProducer, MessageConsumer, and StudentEventPublisher functionality
 */
@Service
public class MessagingService {
    
    private static final Logger logger = LoggerFactory.getLogger(MessagingService.class);
    private static final String MESSAGES_TOPIC = "messages";
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    // Thread-safe list to store consumed messages
    private final List<Message> consumedMessages = new CopyOnWriteArrayList<>();
    
    // Maximum number of messages to keep in memory
    private static final int MAX_MESSAGES = 100;
    
    // ==================== GENERAL MESSAGE METHODS ====================
    
    /**
     * Send a simple text message
     */
    public void sendMessage(String content, String sender) {
        Message message = new Message(content, sender);
        sendMessage(message);
    }
    
    /**
     * Send a Message object
     */
    public void sendMessage(Message message) {
        try {
            logger.info("📤 PRODUCER: Sending message - {}", message.getContent());
            
            CompletableFuture<SendResult<String, Object>> future = 
                kafkaTemplate.send(MESSAGES_TOPIC, message.getId(), message);
            
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info("✅ PRODUCER: Message sent successfully [{}] to topic [{}] at offset [{}]",
                            message.getId(), MESSAGES_TOPIC, result.getRecordMetadata().offset());
                } else {
                    logger.error("❌ PRODUCER: Failed to send message [{}]: {}", 
                            message.getId(), ex.getMessage());
                }
            });
            
        } catch (Exception e) {
            logger.error("❌ PRODUCER: Exception while sending message: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Send multiple test messages
     */
    public void sendTestMessages() {
        String[] testMessages = {
            "Hello from Producer!",
            "This is a test message",
            "Kafka is working great!",
            "Producer-Consumer demo",
            "Real-time messaging system"
        };
        
        for (int i = 0; i < testMessages.length; i++) {
            sendMessage(testMessages[i], "TestProducer");
            try {
                Thread.sleep(1000); // 1 second delay between messages
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    // ==================== MESSAGE CONSUMER METHODS ====================
    
    /**
     * Kafka listener for consuming messages
     */
    @KafkaListener(topics = "messages", groupId = "message-consumer-group")
    public void consumeMessage(
            @Payload Message message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {
        
        try {
            logger.info("📥 CONSUMER: Received message from topic [{}], partition [{}], offset [{}]",
                    topic, partition, offset);
            logger.info("📝 CONSUMER: Message content - ID: {}, Sender: {}, Content: {}, Time: {}",
                    message.getId(), message.getSender(), message.getContent(), message.getTimestamp());
            
            // Store the message for web display
            addMessage(message);
            
            // Acknowledge the message
            acknowledgment.acknowledge();
            logger.info("✅ CONSUMER: Message processed and acknowledged [{}]", message.getId());
            
        } catch (Exception e) {
            logger.error("❌ CONSUMER: Failed to process message [{}]: {}", 
                    message.getId(), e.getMessage(), e);
            // Acknowledge to prevent infinite retries
            acknowledgment.acknowledge();
        }
    }
    
    /**
     * Add message to the in-memory storage
     */
    private void addMessage(Message message) {
        consumedMessages.add(0, message); // Add to beginning for latest-first order
        
        // Keep only the latest MAX_MESSAGES
        if (consumedMessages.size() > MAX_MESSAGES) {
            consumedMessages.subList(MAX_MESSAGES, consumedMessages.size()).clear();
        }
        
        logger.info("📊 CONSUMER: Total messages stored: {}", consumedMessages.size());
    }
    
    // ==================== MESSAGE QUERY METHODS ====================
    
    /**
     * Get all consumed messages (latest first)
     */
    public List<Message> getAllMessages() {
        return new ArrayList<>(consumedMessages);
    }
    
    /**
     * Get latest N messages
     */
    public List<Message> getLatestMessages(int count) {
        if (count >= consumedMessages.size()) {
            return getAllMessages();
        }
        return new ArrayList<>(consumedMessages.subList(0, count));
    }
    
    /**
     * Get total message count
     */
    public int getMessageCount() {
        return consumedMessages.size();
    }
    
    /**
     * Clear all messages
     */
    public void clearMessages() {
        consumedMessages.clear();
        logger.info("🗑️ CONSUMER: All messages cleared");
    }
    
    /**
     * Get messages by sender
     */
    public List<Message> getMessagesBySender(String sender) {
        return consumedMessages.stream()
                .filter(msg -> sender.equals(msg.getSender()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    // ==================== STUDENT EVENT PUBLISHING METHODS ====================
    
    /**
     * Publish student registration event
     */
    public void publishRegistrationEvent(Student student) {
        publishRegistrationEvent(student, "API_FORM");
    }
    
    /**
     * Publish student registration event with custom source
     */
    public void publishRegistrationEvent(Student student, String registrationSource) {
        try {
            StudentEvent event = StudentEvent.createRegistrationEvent(student, registrationSource);
            publishStudentEvent(event, KafkaConfig.STUDENT_EVENTS_TOPIC);
            logger.info("📢 EVENT: Published student registration event for student ID: {}", student.getId());
        } catch (Exception e) {
            logger.error("❌ EVENT: Failed to publish registration event for student ID: {}", student.getId(), e);
        }
    }
    
    /**
     * Publish student deletion event
     */
    public void publishDeletionEvent(Student student, String deletedBy) {
        publishDeletionEvent(student, deletedBy, "Administrative action");
    }
    
    /**
     * Publish student deletion event with custom reason
     */
    public void publishDeletionEvent(Student student, String deletedBy, String reason) {
        try {
            StudentEvent event = StudentEvent.createDeletionEvent(student, deletedBy, reason);
            publishStudentEvent(event, KafkaConfig.STUDENT_EVENTS_TOPIC);
            logger.info("📢 EVENT: Published student deletion event for student ID: {}", student.getId());
        } catch (Exception e) {
            logger.error("❌ EVENT: Failed to publish deletion event for student ID: {}", student.getId(), e);
        }
    }
    
    /**
     * Publish student update event
     */
    public void publishUpdateEvent(Student student, String updatedBy) {
        try {
            StudentEvent event = StudentEvent.createUpdateEvent(student, updatedBy);
            publishStudentEvent(event, KafkaConfig.STUDENT_EVENTS_TOPIC);
            logger.info("📢 EVENT: Published student update event for student ID: {}", student.getId());
        } catch (Exception e) {
            logger.error("❌ EVENT: Failed to publish update event for student ID: {}", student.getId(), e);
        }
    }
    
    /**
     * Generic method to publish any student event
     */
    private void publishStudentEvent(StudentEvent event, String topic) {
        try {
            // Use student ID as the key for partitioning
            String key = event.getStudentId() != null ? event.getStudentId().toString() : event.getEventId();
            
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, event);
            
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info("✅ EVENT: Successfully sent event [{}] to topic [{}] with key [{}] at offset [{}]",
                            event.getEventId(), topic, key, result.getRecordMetadata().offset());
                } else {
                    logger.error("❌ EVENT: Failed to send event [{}] to topic [{}] with key [{}]",
                            event.getEventId(), topic, key, ex);
                }
            });
            
        } catch (Exception e) {
            logger.error("❌ EVENT: Exception occurred while publishing event to topic [{}]: {}", topic, e.getMessage(), e);
            throw e;
        }
    }
}