package com.nazarvalko.lms.security;

import com.nazarvalko.lms.service.UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/image").permitAll()
                                .requestMatchers("/").permitAll()
                                //.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/form").permitAll()
                                .requestMatchers(HttpMethod.POST,"/sign-up").permitAll()
                                .requestMatchers("/confirmation").permitAll()
                                .requestMatchers("/logout").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/dashboard").hasRole("USER")
                                .requestMatchers("/profile").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/user-books").hasRole("USER")
                                .requestMatchers("/reservation-list").hasRole("ADMIN")
                                .requestMatchers("/show-add-book").hasRole("ADMIN")
                                .requestMatchers("/add-book").hasRole("ADMIN")
                                .requestMatchers("/request-book").hasRole("USER")
                                .requestMatchers("/request-list").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/show-users").hasRole("ADMIN")
                                .requestMatchers("/show-issue-book-form").hasRole("ADMIN")
                                .requestMatchers("/issue-book").hasRole("ADMIN")
                                .requestMatchers("/issued-books").hasRole("USER")
                                .requestMatchers("/borrowed").hasRole("ADMIN")
                                .requestMatchers("/archived").hasRole("USER")
                                .requestMatchers("/add-user").hasRole("ADMIN")
                                .requestMatchers("/add-user-form").hasRole("ADMIN")
                                .requestMatchers("/edit-user").hasRole("ADMIN")
                                .requestMatchers("/delete-user").hasRole("ADMIN")
                                .requestMatchers("/approve-request").hasRole("ADMIN")
                                .requestMatchers("/reject-request").hasRole("ADMIN")
                                .requestMatchers("/delete-book").hasRole("ADMIN")
                                .requestMatchers("/categories").hasRole("ADMIN")
                                .requestMatchers("/add-category").hasRole("ADMIN")
                                .requestMatchers("/delete-category").hasRole("ADMIN")
                                .requestMatchers("/update-category").hasRole("ADMIN")
                                .requestMatchers("/view-book").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/update-book").hasRole("ADMIN")
                                .requestMatchers("/return").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateUser")
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                );

        return http.build();
    }

}
