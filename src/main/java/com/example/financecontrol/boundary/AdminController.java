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
        model.addAttribute("adminid", authenticatedUser.getId());
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
            if (userService.getUserByUsername(username) != null) {
                redirectAttributes.addFlashAttribute("message", "This username already exists!");
            } else {
                if (!user.getFullname().equals(fullname)) {
                    changes += "{" + user.getFullname() + " to " + fullname + "}";
                    user.setFullname(fullname);
                }
                if (!password.equals(user.getPassword())) {
                    changes += "{Old Password to " + password + "}";
                    user.setPassword(passwordEncoder.encode(password));
                }
                userService.updateUser(user);
                if (changes.equals("")) {
                    redirectAttributes.addFlashAttribute("message", "Nothing changed!");
                } else {
                    redirectAttributes.addFlashAttribute("message", "User data successfully updated" + changes);
                }
            }
        }
        return redirectView;
    }

    @PostMapping("/delete/{id}")
    public RedirectView deleteUserById(
            @PathVariable Integer id,
            @RequestParam String password,
            RedirectAttributes redirectAttributes
    ) {
        RedirectView redirectView = new RedirectView("/admin", true);
        if (passwordEncoder.matches(password, authenticatedUser.getPassword())) {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Incorrect password!");
        }
        return redirectView;
    }

    @PostMapping("/new")
    public RedirectView createNewUser(
            RedirectAttributes redirectAttributes,
            @RequestParam String fullname,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword
    ) {
        RedirectView redirectView = new RedirectView("/admin", true);
        User user = userService.getUserByUsername(username);
        if (user == null) {
            if (password.equals(confirmPassword)) {
                userService.createUser(
                        fullname,
                        username,
                        password,
                        Role.USER
                );
                redirectAttributes.addFlashAttribute("message", "User added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Passwords do not match!");
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Username already exists!");
        }
        return redirectView;
    }

    @PostMapping("/changepass/{adminid}")
    public RedirectView changeAdminPassword(
            @PathVariable Integer adminid,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmNewPassword,
            RedirectAttributes redirectAttributes
    ) {
        RedirectView redirectView = new RedirectView("/admin", true);
        User user = userService.getUserById(adminid);
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            if (newPassword.equals(confirmNewPassword)) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userService.updateUser(user);
                redirectAttributes.addFlashAttribute("message", "Password updated!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Passwords do not match!");
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Incorrect password, try again!");
        }
        return redirectView;
    }
}
