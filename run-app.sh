#!/bin/bash
echo "========================================"
echo "Student Management System Startup"
echo "========================================"
echo
echo "Prerequisites:"
echo "1. MySQL should be running on localhost:3306"
echo "2. Database 'student_management_db' will be created automatically"
echo "3. Kafka is optional (localhost:9092)"
echo
echo "Building all modules first..."
mvn clean install -DskipTests
if [ $? -ne 0 ]; then
    echo "Build failed! Please check the error messages above."
    exit 1
fi
echo
echo "Starting main application..."
echo
echo "Once started, visit: http://localhost:8080"
echo "Test credentials: admin/admin123, teacher/teacher123, student1/student123"
echo
cd main-app
mvn spring-boot:run
cd ..