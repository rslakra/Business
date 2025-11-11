package com.rslakra.vantageservice.account.controller;

import com.rslakra.vantageservice.account.persistence.entity.User;
import com.rslakra.vantageservice.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 8/20/21 6:51 PM
 */
@Controller
@RequestMapping("/accounts")
public class AccountController {
    
    private final UserService userService;
    
    /**
     * @param userService
     */
    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/profile")
    public String listProfile(Model model) {
        // TODO: Replace with logged-in user retrieval
        // User loggedInUser = getCurrentLoggedInUser();
        
        // For now, get the first user from database or create a test user
        User user = null;
        List<User> users = userService.getAll();
        if (!users.isEmpty()) {
            // Use the first user (later this will be the logged-in user)
            user = users.get(0);
        } else {
            // If no users exist, create a test user with an ID for demo purposes
            user = new User();
            user.setId(1L); // Set a test ID so edit button works
            user.setFirstName("Roh");
            user.setLastName("Lak");
        }
        model.addAttribute("user", user);
        return "views/account/profile";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "index";
    }
    
}
