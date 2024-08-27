package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        loadRoles();
        loadUsers();
    }

    private void loadRoles() {
        if (roleRepository.count() == 0) { // Проверяем, есть ли уже роли
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.saveAll(Arrays.asList(adminRole, userRole));
            System.out.println("Roles loaded: " + Arrays.asList(adminRole, userRole));
        }
    }

    private void loadUsers() {
        if (userRepository.count() == 0) { // Проверяем, есть ли уже пользователи
            // Создание администратора
            User admin = new User();
            admin.setName("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@admin.com");
            admin.setRoles(Collections.singleton(roleRepository.findByName("ROLE_ADMIN")));
            userRepository.save(admin);

            // Создание обычного пользователя
            User user = new User();
            user.setName("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setEmail("user@user.com");
            user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));
            userRepository.save(user);

            System.out.println("Users loaded: " + Arrays.asList(admin, user));
        }
    }
}