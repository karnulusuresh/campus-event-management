package com.campus.event.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.campus.event.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	 private final JwtAuthenticationFilter jwtFilter;
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	     http
	         .csrf().disable()
	         .cors() // ✅ enable CORS
	         .and()
	         .authorizeHttpRequests(auth -> auth
	                 .requestMatchers("/api/auth/**", "/api/users").permitAll()
	                 .anyRequest().authenticated()
	         )
	         .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	         .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	     return http.build();
	 }

    
	 @Bean
	 public CorsConfigurationSource corsConfigurationSource() {
	     CorsConfiguration config = new CorsConfiguration();

	     config.addAllowedOrigin("http://127.0.0.1:5500"); // ✅ VS Code Live Server
	     config.addAllowedOrigin("http://localhost:5500"); // optional
	     config.addAllowedOrigin("http://localhost:3000"); // if using React later
	     
	     config.addAllowedMethod("*"); // GET, POST, PUT, DELETE
	     config.addAllowedHeader("*");
	     config.setAllowCredentials(true);

	     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	     source.registerCorsConfiguration("/**", config);

	     return source;
	 }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
