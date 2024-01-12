package com.nazarvalko.lms.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    private UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/", "/image", "/form", "/sign-up", "/confirmation", "/logout",
                                        "/login").permitAll()
                                .requestMatchers("/dashboard", "/user-books", "request-book", "/issued-books",
                                        "/archived").hasRole("USER")
                                .requestMatchers("/reservation-list", "/show-add-book", "/add-book",
                                        "/show-users", "/show-issue-book-form", "/issue-book", "/borrowed",
                                        "/add-user", "/add-user-form", "/edit-user", "/delete-user",
                                        "/approve-request", "/reject-request", "/delete-book",
                                        "/categories", "/add-category", "/delete-category",
                                        "/update-category", "/update-book", "/return").hasRole("ADMIN")
                                .requestMatchers("/profile", "/request-list", "/view-book").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated()
                )

                .formLogin(form -> form
                                .loginPage("/login")
                                .usernameParameter("email")
                                .permitAll()
                )
                .logout(logout -> logout
                        .deleteCookies("email")
                        .logoutUrl("/logout")
                        .permitAll()
                );

        return http.build();
    }

}
