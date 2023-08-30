package com.nazarvalko.lms.repository;

import com.nazarvalko.lms.entity.User;
import java.util.List;

public interface UserRepository {

    User loadUserByUsername(String username);

    void updateProfile(User user);

    List<User> getAllUsers();

    User findUserById(int id);

    User findUserByUsername(String username);

    void deleteUser(int id);

    void deleteUserByUsername(String username);

    void addNewUser(User user);

    long countAllUsers();

}
