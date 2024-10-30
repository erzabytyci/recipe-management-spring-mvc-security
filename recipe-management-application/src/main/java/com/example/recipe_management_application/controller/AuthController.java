package com.example.recipe_management_application.controller;

import com.example.recipe_management_application.model.User;
import com.example.recipe_management_application.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    public UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {


        //manually check length of password
        if (user.getPassword().length() < 4) {
            result.rejectValue("password", "error.user", "Password must be at least 4 characters long.");
            return "register";
        }

        // Check if there are validation errors
        if (result.hasErrors()) {
            return "register";
        }

        // Check if the username or email is already taken
        if (userService.findByUsername(user.getUsername()) != null) {
            result.rejectValue("username", "error.user", "Username is already taken!");
            redirectAttributes.addFlashAttribute("errorMessage", "Username already exists!");
            return "redirect:/register";
        }

        // If no error, proceed to register the user
        userService.registerUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "Successfully registered!");

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "auth/login";
    }
}
