package com.sms.config;

import com.sms.filters.JwtAuthenticationCustomFilter;
import com.sms.filters.JwtAuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;

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

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationCustomFilter jwtAuthenticationCustomFilter;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationCustomFilter jwtAuthenticationCustomFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationCustomFilter = jwtAuthenticationCustomFilter;
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthenticationCustomFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("**/addstudent",
                        "**/updatestudent/{id}",
                        "**/deletestudent/{id}").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated();

        http.cors().configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {// managing CORS layers so that 2 different server can communicate  
				CorsConfiguration corsConfig = new CorsConfiguration();
				
					corsConfig.setAllowedOrigins(Collections.singletonList("http://127.0.0.1:5501"));
					corsConfig.setAllowedMethods(Collections.singletonList("*"));
					corsConfig.setAllowCredentials(true);
					corsConfig.setAllowedHeaders(Collections.singletonList("*"));
					corsConfig.setMaxAge(3600L);
					return corsConfig;
			}
		});
        
        http.addFilterBefore(jwtAuthenticationCustomFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}