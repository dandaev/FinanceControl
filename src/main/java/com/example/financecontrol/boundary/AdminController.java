package com.example.financecontrol.boundary;

import com.example.financecontrol.domain.Role;
import com.example.financecontrol.domain.User;
import com.example.financecontrol.service.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private User authenticatedUser;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String getAdminHome(Principal principal, Model model) {
        if (authenticatedUser == null) {
            authenticatedUser = userService.getUserByUsername(principal.getName());
        }
        model.addAttribute("name", authenticatedUser.getFullname());
        List<User> users = new ArrayList<>();
        for (User user : userService.listAllUsers()) {
            if (user.getRole().equals(Role.USER)) {
                users.add(user);
            }
        }
        model.addAttribute("users", users);
        return "admin_home_page";
    }

    @GetMapping("/")
    public String redirectToAdminHome() {
        return "redirect:/admin";
    }

    @PostMapping("/update/{id}")
    public RedirectView updateUserInfo(
            @PathVariable Integer id,
            @RequestParam String fullname,
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes
    ) {
        User user = userService.getUserById(id);
        RedirectView redirectView = new RedirectView("/admin", true);
        String changes = "";
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "User not found!");
        } else {
            if (!user.getFullname().equals(fullname)) {
                changes += "{" + user.getFullname() + " to " + fullname + "}";
                user.setFullname(fullname);
            }
            if (!user.getUsername().equals(username)) {
                changes += "{" + user.getUsername() + " to " + username + "}";
                user.setUsername(username);
            }
            if (!passwordEncoder.matches(password, user.getPassword())) {
                changes += "{Old Password to " + password + "}";
                user.setPassword(passwordEncoder.encode(password));
            }
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "User data successfully updated" + changes);
        }
        return redirectView;
    }
}
