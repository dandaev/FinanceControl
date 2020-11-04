package com.example.financecontrol.boundary;

import com.example.financecontrol.domain.User;
import com.example.financecontrol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private User authenticatedUser;
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getAdminHome(Principal principal, Model model) {
        if (authenticatedUser == null) {
            authenticatedUser = userService.getUserByUsername(principal.getName());
        }
        model.addAttribute("name", authenticatedUser.getFullname());
        model.addAttribute("users", userService.listAllUsers());
        return "admin_home_page";
    }
}
