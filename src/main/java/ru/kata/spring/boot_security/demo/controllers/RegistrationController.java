package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/registration")
    public User registration() {
        roleRepository.findAll();
        return new User();
    }

    @PostMapping("/registration")
    public User addUser(@RequestBody User userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("There is a mistake in provided data");
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            throw new RuntimeException("Password error");
        }
        if (!userService.createUser(userForm, roleRepository.findAll())){
            throw new RuntimeException("User with such username already exists");
        }
        userService.createUser(userForm, roleRepository.findAll());

        return userForm;
    }
}
