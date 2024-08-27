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

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(ModelMap model, Principal principal) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        if (principal != null) {
            User user = userService.getUserByName(principal.getName());
            model.addAttribute("loggedInAdmin", user);
        }

        return "admin";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap model, Principal principal) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("loggedInAdmin", user);
        return "admin"; // имя вашего шаблона
    }
}