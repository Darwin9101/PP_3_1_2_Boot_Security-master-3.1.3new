package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Set;

public interface ServiceInt {

    void insertUser(User user);

    void deleteUser(Long id);

    List<User> getAllUsers();

    User getUserById(Long id);

    void updateUser(String name, String email, String password, Set<Role> roles, Long id);

    User getUserByName(String name);

}