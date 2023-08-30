package com.nazarvalko.lms.repository;

import com.nazarvalko.lms.entity.Role;
import com.nazarvalko.lms.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User loadUserByUsername(String username) {
        TypedQuery<User> query =
                entityManager.createQuery("select u from User u where u.username=:data", User.class);
        query.setParameter("data", username);

        User temp = query.getSingleResult();

        return temp;
    }

    @Override
    @Transactional
    public void updateProfile(User user) {
        User existingUser = findUserByUsername(user.getUsername());

        if (existingUser != null) {

            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setAddress(user.getAddress());
            existingUser.setPhone(user.getPhone());
            existingUser.setPhotoUrl(user.getPhotoUrl());

            addNewUser(existingUser);

        } else {

            addNewUser(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u join u.roles r where r.name = 'ROLE_USER'").getResultList();
    }

    @Override
    public User findUserById(int id) {
        TypedQuery<User> query =
                entityManager.createQuery("select u from User u where u.id=:data", User.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Override
    public User findUserByUsername(String username) {
        TypedQuery<User> query =
                entityManager.createQuery("select u from User u where u.username=:data", User.class);
        query.setParameter("data", username);

        try {
            return query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }

    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        User userToDelete = findUserById(id);
        if (userToDelete != null) {

            entityManager.remove(userToDelete);
        }
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        User userToDelete = findUserByUsername(username);
        if (userToDelete != null) {
            entityManager.remove(userToDelete);
        }
    }

    @Override
    @Transactional
    public void addNewUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public long countAllUsers() {
        TypedQuery<Long> query =
                entityManager.createQuery("select count(u) from User u join u.roles r where r.name = 'ROLE_USER'", Long.class);
        return query.getSingleResult();
    }
}
