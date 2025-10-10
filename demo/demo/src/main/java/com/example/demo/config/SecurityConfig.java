package com.example.demo.config;

import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    ProductService service;
    @Autowired
    private JwtFilter jwtfilt;
    @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
             http
                    .csrf(custmizer-> custmizer.disable())
                    .authorizeHttpRequests(request-> request
                            .requestMatchers("/login","/registor")
                            .permitAll()
                            .anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults())
                    .sessionManagement(session->session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                     .addFilterBefore(jwtfilt, UsernamePasswordAuthenticationFilter.class);
                   return http.build();
        // http.formLogin(Customizer.withDefaults());
        }
//        @Bean
//        public UserDetailsService userDetailsService(){
//        UserDetails user1= User
//                .withDefaultPasswordEncoder()
//                .username("harsha")
//                .password("123321")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1);
//        }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        authenticationProvider.setUserDetailsService(service);
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cofig) throws Exception {
        return cofig.getAuthenticationManager();
    }

}
