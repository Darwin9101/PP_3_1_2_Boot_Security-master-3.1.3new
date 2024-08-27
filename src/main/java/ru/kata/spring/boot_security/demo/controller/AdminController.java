package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceInt;
import ru.kata.spring.boot_security.demo.service.ServiceInt;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ServiceInt userService;
    private final RoleServiceInt roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String index(ModelMap model, Principal principal) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        Set<Role> roles = new HashSet<>();
        roles.addAll(roleService.getAllRoles());
        model.addAttribute("roles", roles);

        if (principal != null) {
            User user = userService.getUserByName(principal.getName());
            model.addAttribute("loggedInAdmin", user);
        }

        return "admin";
    }


    @PostMapping("/create")
    @Transactional
    public String createUser(@RequestParam String name,
                             @RequestParam String email,
                             @RequestParam(required = false) String password,
                             @RequestParam List<Long> roles) {


        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        // Установите роли
        Set<Role> selectedRoles = new HashSet<>();
        for (Long roleId : roles) {
            Role role = roleService.getRoleById(roleId);
            selectedRoles.add(role);
        }
        user.setRoles(selectedRoles); // Установите роли пользователю

        userService.insertUser(user);
        return "redirect:/admin"; // Перенаправление на список пользователей
    }

    @PostMapping("/edit")
    public String updateUser(@RequestParam String name,
                             @RequestParam String email,
                             @RequestParam(required = false) String password, // Пароль будет необязательным
                             @RequestParam Set<Role> roles,
                             @RequestParam Long id) {
        userService.updateUser(name, email, password, roles, id);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    @Transactional
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // Измененный вызов
        return "redirect:/admin"; // Перенаправление после удаления
    }

}