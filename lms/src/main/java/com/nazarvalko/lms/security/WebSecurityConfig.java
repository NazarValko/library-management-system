package com.nazarvalko.lms.security;

import com.nazarvalko.lms.filters.CookiesAuthenticationFilter;
import com.nazarvalko.lms.filters.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

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
//                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterAfter(new CookiesAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                        .successHandler(authenticationSuccessHandler)
                        .loginPage("/login")
                        .usernameParameter("email")
                        .permitAll()
                )
                .logout(logout -> logout
                        .deleteCookies("jwt")
                        .logoutUrl("/logout")
                        .permitAll()
                );

        return http.build();
    }

}
