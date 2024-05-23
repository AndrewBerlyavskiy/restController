package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;
    @GetMapping
    public String adminPage () {
        return "admin";
    }
    @GetMapping("/{id}")
    public User adminPageGetUser (@PathVariable Long id) {
        User user = userService.getUserById(id);
        List<Role> allRoles = roleRepository.findAll();
        return user;
    }
    @PutMapping("/{id}")
    public User adminPageUpdateUser (@PathVariable Long id, @RequestBody User user, String password) {
        user.setId(id);
        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userService.updateUser(user, password);
        return user;
    }

    @GetMapping("/delete/{id}")
    public User adminPageGetUserForRemoval (@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user.getId() == null) {
            throw new RuntimeException("No such user in database");
        }
        List<Role> allRoles = roleRepository.findAll();
        return user;
    }
    @DeleteMapping("/delete/{id}")
    public String adminPageDeleteUser (@PathVariable Long id) {
        userService.userRemoval(id);
        return "Employee with id = " + id + " was deleted";
    }

    @GetMapping("/users")
    public List<User> showAll () {
        List<User> users = userService.listOfUsers();
        return users;
    }
//    public String adminPage (@ModelAttribute("userAdmin") User userAdmin, Model model) {
//        if
////        String roleName = "ROLE_ADMIN";
////        User user = userService.findByUsername(username);
////        Optional<Role> optionalRole = user.getRoles().stream().filter(r -> r.getName().equals(roleName)).findAny();
////        Role role = optionalRole.orElse(new Role());
////        if (role.getName().equals(roleName)) model.addAttribute("userAdmin", user);
//        return "admin";
//    }
}
