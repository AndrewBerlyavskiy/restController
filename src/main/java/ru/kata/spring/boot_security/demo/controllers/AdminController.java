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

@Controller
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
    public String adminPageGetUser (@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> allRoles = roleRepository.findAll();
        model.addAttribute("getUser", user);
        model.addAttribute("allRoles", allRoles);
        return "adminUserUpdate";
    }
    @PostMapping("/{id}")
    public String adminPageUpdateUser (Model model, @PathVariable Long id, @ModelAttribute("getUser") User user, @RequestParam String password, @RequestParam(name = "roles") List<Role> selectedRoles) {
        user.setId(id);
        user.setRoles(selectedRoles);
        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userService.updateUser(user, password);
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String adminPageGetUserForRemoval (Model model, @PathVariable Long id) {
        User user = userService.getUserById(id);
        List<Role> allRoles = roleRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "adminUserDelete";
    }
    @PostMapping("/delete/{id}")
    public String adminPageDeleteUser (Model model, @PathVariable Long id) {
        userService.userRemoval(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/users")
    public String showAll (Model model) {
        model.addAttribute("list", userService.listOfUsers());
        return "usersList";
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
