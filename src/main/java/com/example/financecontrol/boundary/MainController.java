package com.example.financecontrol.boundary;

import com.example.financecontrol.domain.Payment;
import com.example.financecontrol.domain.User;
import com.example.financecontrol.service.PaymentService;
import com.example.financecontrol.service.StockService;
import com.example.financecontrol.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    private User authenticatedUser;

    private final UserService userService;
    private final PaymentService paymentService;
    private final StockService stockService;

    public MainController(UserService userService, PaymentService paymentService, StockService stockService){
        this.userService = userService;
        this.paymentService=paymentService;
        this.stockService = stockService;
    }

    @GetMapping("/")
    public String getHome(Principal principal, Model model) {
        //if (authenticatedUser == null) {
        authenticatedUser = userService.getUserByUsername(principal.getName());
        //}
        model.addAttribute("authenticatedUser", authenticatedUser);
        model.addAttribute("users", userService.listAllUsersWithRoleUser());
        model.addAttribute("spendMoney", stockService.getUserSpendMoney(authenticatedUser));
        model.addAttribute("userDebMap", userService.getUserDebList(authenticatedUser));
        return "home";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPayment(@RequestParam Double amount,
                                @RequestParam String check,
                                    @RequestParam List<Integer> choosedUsers ){
        Payment payment = new Payment(amount,authenticatedUser,check);
        paymentService.createPayment(payment);
        for (Integer i:choosedUsers){
            stockService.createStock(payment,userService.getUserById(i));
        }
        return "redirect:/";
    }

}
