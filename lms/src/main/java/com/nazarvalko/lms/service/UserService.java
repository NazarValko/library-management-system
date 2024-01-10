package com.nazarvalko.lms.service;

import com.nazarvalko.lms.entity.Role;
import com.nazarvalko.lms.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

public interface UserService {


    void updateProfile(User user, MultipartFile file);

    List<User> getAllUsers();

    User findUserById(int id);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    void deleteUser(int id);

    void deleteUserByUsername(String username);

    void addNewUser(User user);

    long countAllUsers();

    Collection<Role> getUserRoles(User user);
}
