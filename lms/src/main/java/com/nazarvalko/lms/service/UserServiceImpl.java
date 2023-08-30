package com.nazarvalko.lms.service;

import com.nazarvalko.lms.entity.Role;
import com.nazarvalko.lms.entity.User;
import com.nazarvalko.lms.repository.RoleRepository;
import com.nazarvalko.lms.repository.UserRepository;
import com.nazarvalko.lms.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User temp = userRepository.loadUserByUsername(username);
        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(temp.getRoles());
        return new org.springframework.security.core.userdetails.User(temp.getUsername(),
                temp.getPassword(), authorities);
    }
    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities =  new ArrayList<>();

        for (Role role : roles) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(simpleGrantedAuthority);
        }
        return authorities;
    }
    @Override
    public void updateProfile(User user, MultipartFile file) {
        try {
            user.setPhotoUrl(ImageUtil.compressImage(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userRepository.updateProfile(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findUserById(id);

        user.setRoles(null);
        userRepository.deleteUser(id);
    }

    @Override
    public void deleteUserByUsername(String username) {
        userRepository.deleteUserByUsername(username);
    }

    @Override
    public void addNewUser(User user) {
        User temp = new User();


        if (user.getUsername() == null) {
            temp.setUsername(user.getEmail());
        } else {
            temp.setUsername(user.getUsername());
        }

        temp.setPassword(passwordEncoder.encode(user.getPassword()));
        temp.setFirstName(user.getFirstName());
        temp.setLastName(user.getLastName());
        temp.setEmail(user.getEmail());
        temp.setAddress(user.getAddress());
        temp.setPhone(user.getPhone());
        temp.setRoles(Arrays.asList(roleRepository.findRoleByName("ROLE_USER")));

        userRepository.addNewUser(temp);
    }

    @Override
    public long countAllUsers() {
        return userRepository.countAllUsers();
    }

    @Override
    public Collection<Role> getUserRoles(User user) {
        return user.getRoles();
    }


}
