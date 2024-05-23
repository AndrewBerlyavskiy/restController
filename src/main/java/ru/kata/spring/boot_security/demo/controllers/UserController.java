package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/{username}")
    @ResponseBody
    public ResponseEntity<User> userPage (@PathVariable String username) {
        User user = userService.findByUsername(username);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
