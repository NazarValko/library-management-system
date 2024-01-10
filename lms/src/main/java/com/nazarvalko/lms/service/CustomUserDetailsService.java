package com.nazarvalko.lms.service;

import com.nazarvalko.lms.entity.Role;
import com.nazarvalko.lms.entity.User;
import com.nazarvalko.lms.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User temp = userRepository.findUserByEmail(username);
        Collection<SimpleGrantedAuthority> authorities =
        temp.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();
        return new org.springframework.security.core.userdetails.User(temp.getEmail(),
                temp.getPassword(), authorities);
    }
}
