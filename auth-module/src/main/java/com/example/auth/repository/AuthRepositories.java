package com.example.auth.repository;

import com.example.auth.model.RefreshToken;
import com.example.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Consolidated Auth Repositories
 * Contains all repository interfaces for authentication-related entities
 */
public class AuthRepositories {

    /**
     * Repository for User entity
     */
    @Repository
    public interface UserRepository extends JpaRepository<User, Long> {
        
        /**
         * Find user by username
         */
        Optional<User> findByUsername(String username);
        
        /**
         * Find user by email
         */
        Optional<User> findByEmail(String email);
        
        /**
         * Find user by roll number
         */
        Optional<User> findByRollNo(Integer rollNo);
        
        /**
         * Check if username exists
         */
        boolean existsByUsername(String username);
        
        /**
         * Check if email exists
         */
        boolean existsByEmail(String email);
        
        /**
         * Check if roll number exists
         */
        boolean existsByRollNo(Integer rollNo);
        
        /**
         * Find user by username or email
         */
        @Query("SELECT u FROM User u WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail")
        Optional<User> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
        
        /**
         * Find enabled users by username
         */
        Optional<User> findByUsernameAndEnabled(String username, boolean enabled);
    }

    /**
     * Repository for RefreshToken entity
     */
    @Repository
    public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
        
        /**
         * Find refresh token by token string
         */
        Optional<RefreshToken> findByToken(String token);
        
        /**
         * Find all refresh tokens for a user
         */
        List<RefreshToken> findByUser(User user);
        
        /**
         * Find valid refresh tokens for a user
         */
        @Query("SELECT rt FROM RefreshToken rt WHERE rt.user = :user AND rt.revoked = false AND rt.expiresAt > :now")
        List<RefreshToken> findValidTokensByUser(@Param("user") User user, @Param("now") LocalDateTime now);
        
        /**
         * Delete all refresh tokens for a user
         */
        void deleteByUser(User user);
        
        /**
         * Delete expired refresh tokens
         */
        @Modifying
        @Query("DELETE FROM RefreshToken rt WHERE rt.expiresAt < :now")
        void deleteExpiredTokens(@Param("now") LocalDateTime now);
        
        /**
         * Revoke all tokens for a user
         */
        @Modifying
        @Query("UPDATE RefreshToken rt SET rt.revoked = true WHERE rt.user = :user")
        void revokeAllTokensByUser(@Param("user") User user);
        
        /**
         * Revoke specific token
         */
        @Modifying
        @Query("UPDATE RefreshToken rt SET rt.revoked = true WHERE rt.token = :token")
        void revokeToken(@Param("token") String token);
    }
}