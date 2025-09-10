package com.example.Student_form.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for login module
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    
    /**
     * Add view controllers for simple redirects
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // You can add simple view mappings here if needed
        // registry.addViewController("/login/help").setViewName("login/help");
    }
}