package com.booking.controller;

import java.util.List;

import com.booking.entity.Role;
import com.booking.entity.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.booking.service.UserService;

@Controller
public class AuthController {
	 private final UserService userService;

	    public AuthController(UserService userService) {
	        this.userService = userService;
	    }

	    @GetMapping("/register")
	    public String showRegistrationForm(Model model) {
	        Users user = new Users();
	        List<Role> roles = userService.getAllRoles(); // Fetch all roles dynamically
	        model.addAttribute("user", user);
	        model.addAttribute("roles", roles); // Pass roles to the view
	        return "register";
	    }

	    @PostMapping("/register")
	    public String registerUser(@ModelAttribute("user") Users user, String roleName, Model model) {
	        if (userService.findByUsername(user.getUsername()) != null) {
	            model.addAttribute("error", "Username is already taken");
	            return "register";
	        }
			if (userService.existsByEmail(user.getEmail())) {
				model.addAttribute("error", "Email is already taken");
				return "register";
			}
	        userService.registerUser(user, roleName);
	        return "redirect:/login";
	    }

	    @GetMapping("/login")
	    public String showLoginForm() {
	        return "login";
	    }

}
