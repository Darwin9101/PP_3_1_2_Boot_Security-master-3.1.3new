package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceInt;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {

    private final ServiceInt userService;

    @Autowired
    public UserController(ServiceInt userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUser(ModelMap model, Principal principal) {
        User curentUser = userService.getUserByName(principal.getName());
        model.addAttribute("user", curentUser);
        return "user";
    }
}
