package com.example.recipe_management_application.controller;

import com.example.recipe_management_application.model.User;
import com.example.recipe_management_application.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);

        if (user != null) {
            model.addAttribute("user", user);
        } else {
            return "redirect:/login";
        }

        return "auth/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User updatedUser,
                                @RequestParam("password") String password,
                                Principal principal, RedirectAttributes redirectAttributes)
    {
        User user = userService.findByUsername(principal.getName());

        // Update username
        user.setUsername(updatedUser.getUsername());

        // Update email
        user.setEmail(updatedUser.getEmail());

        // Only update the password if the user provided a new one
        if (password != null && !password.isEmpty()) {

            if (password.length() < 4) {
                redirectAttributes.addFlashAttribute("errorMessage", "Password must be at least 4 characters long.");
                return "redirect:/auth/edit-profile";  // Redirect back to the edit profile page
            }


            // Ensure the password is encoded before saving
            user.setPassword(passwordEncoder.encode(password));
        }

        // Save updated user details
        userService.registerUser(user);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authentication.getAuthorities());

        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "redirect:/profile";  // Redirect back to profile page with updated data
    }

    @PostMapping("/profile/delete")
    public String deleteProfile(Principal principal, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.findByUsername(principal.getName());

        // Delete the user
        userService.delete(user);

        // Manually logout the user after deletion
        try {
            request.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        // Redirect to login page after deletion
        return "redirect:/login?logout";
    }

    @GetMapping("/auth/edit-profile")
    public String showProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "auth/edit-profile";
    }

}
