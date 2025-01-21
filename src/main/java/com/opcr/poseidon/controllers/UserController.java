package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.User;
import com.opcr.poseidon.services.UserService;
import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result) {
        if (!result.hasErrors()) {
            userService.saveUser(user);
            logger.info("CREATE : User %s".formatted(user));
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            user.get().setPassword("");
            model.addAttribute("user", user.get());
            return "user/update";
        }
        return "redirect:/user/list";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "user/update";
        } else {
            userService.updateUserById(id, user);
            logger.info("UPDATE : User id %s".formatted(id));
            return "redirect:/user/list";
        }
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        logger.info("DELETE : User id %s".formatted(id));
        return "redirect:/user/list";
    }
}
