package com.example.financecontrol.boundary;

import com.example.financecontrol.domain.Payment;
import com.example.financecontrol.domain.User;
import com.example.financecontrol.service.PaymentService;
import com.example.financecontrol.service.StockService;
import com.example.financecontrol.service.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        model.addAttribute("unconfirmed_stocks", stockService.getAllUnconfirmedStocksByUserId(authenticatedUser.getId()));
        model.addAttribute("stocks", stockService.getAllStocks());
        return "home";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPayment(@RequestParam Double amount,
//                                @RequestParam String check,
                             @RequestParam("file") MultipartFile file,
                                    @RequestParam List<Integer> choosedUsers ){
        byte[] check = new byte[0];
        try {
            check = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/";
        }
        Payment payment = new Payment(amount,authenticatedUser,check);
        paymentService.createPayment(payment);
        for (Integer i:choosedUsers){
            stockService.createStock(payment,userService.getUserById(i));
        }
        return "redirect:/";
    }

    @GetMapping("/image/{payment_id}")
    public void showPaymentImage(
            @PathVariable String payment_id,
            HttpServletResponse response
    ) throws IOException {
        response.setContentType("image/jpg");
        Payment payment = paymentService.getPaymentById(Integer.parseInt(payment_id));
        InputStream is = new ByteArrayInputStream(payment.getCheque());
        IOUtils.copy(is, response.getOutputStream());
    }

    @GetMapping("/confirm_sharing/{user_id}/{stock_id}")
    public String confirmStockSharing(
            @PathVariable String user_id,
            @PathVariable String stock_id
    ) {
        if (authenticatedUser.getId().equals(Integer.parseInt(user_id))) {
            stockService.confirmStockSharing(Integer.parseInt(stock_id));
            System.out.println("confirmed");
        }
        return "redirect:/";
    }
}
