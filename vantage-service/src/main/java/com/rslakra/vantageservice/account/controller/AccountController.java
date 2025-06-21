package com.rslakra.vantageservice.account.controller;

import com.rslakra.vantageservice.account.persistence.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Rohtash Lakra
 * @created 8/20/21 6:51 PM
 */
@Controller
@RequestMapping("/accounts")
public class AccountController {
    
    @GetMapping("/profile")
    public String listProfile(Model model) {
        User user = new User();
        user.setFirstName("Roh");
        user.setLastName("Lak");
        user.setDob(Date.from(Instant.from(Instant.now())));
        model.addAttribute("users", Arrays.asList(user));
        return "views/account/profile";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "index";
    }
    
}
