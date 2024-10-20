package com.javaded78.timetracker.security.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(
            final HttpSecurity httpSecurity
    ) {
        httpSecurity
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .httpBasic((AbstractHttpConfigurer::disable))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint((
                                        request, response, authException
                                ) -> {
                                    response.setStatus(HttpStatus.UNAUTHORIZED
                                            .value());
                                    response.getWriter().write("Unauthorized.");
                                })
                                .accessDeniedHandler((
                                        request, response, accessDeniedException
                                ) -> {
                                    response.setStatus(HttpStatus.FORBIDDEN
                                            .value());
                                    response.getWriter().write("Unauthorized.");
                                })
                )
                .authorizeHttpRequests(authHttpRequests -> authHttpRequests
                        .requestMatchers("/api/v1/users/**").permitAll()
                        .requestMatchers("/api/v1/projects/**").permitAll()
                        .anyRequest().authenticated())
                .anonymous(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

}
