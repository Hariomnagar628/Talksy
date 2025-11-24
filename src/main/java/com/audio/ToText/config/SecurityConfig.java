package com.audio.ToText.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // disable for development; enable later for security
            .authorizeHttpRequests(auth -> auth
    .requestMatchers(
            "/auth/register",
            "/auth/login",
            "/login",
            "/css/**",
            "/js/**",
            "/upload",
            "/files/**",
            "/ws-chat/**"     // ADD THIS
    ).permitAll()
    .anyRequest().authenticated()
)

            .formLogin(form -> form
                .loginPage("/auth/login")         // GET login page (AuthController)
                .loginProcessingUrl("/login")     // POST from <form th:action="@{/login}">
                .defaultSuccessUrl("/chat", true) // after success
                .failureUrl("/auth/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login?logout=true")
                .permitAll()
            );

        return http.build();
    }
}
