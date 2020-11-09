package com.example.financecontrol.service.implementations;

import com.example.financecontrol.domain.Role;
import com.example.financecontrol.domain.Stock;
import com.example.financecontrol.domain.User;
import com.example.financecontrol.repository.UserRepository;
import com.example.financecontrol.service.PaymentService;
import com.example.financecontrol.service.StockService;
import com.example.financecontrol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StockService stockService;
    private final PaymentService paymentService;
    public UserServiceImpl(UserRepository userRepository, StockService stockService, PaymentService paymentService) {
        this.userRepository = userRepository;
        this.stockService = stockService;
        this.paymentService = paymentService;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }


    @Override
    public boolean createUser(String fullname, String username, String password, Role role) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            user = new User(
                    fullname,
                    username,
                    password,
                    role
            );
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User deleteUser(Integer id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            return null;
        }
        userRepository.delete(user);
        return user;
    }

    @Override
    public boolean updateUser(User user) {
        if (userRepository.getUserById(user.getId()) == null) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.getUserById(id);
    }

    @Override
    public List<User> listAllUsersWithRoleUser() {
        return userRepository.getUsersByRole(Role.USER);
    }

    @Override
    public HashMap<User,Double> getUserDebList(User authenticatedUser) {
        try {
            List<User> userList = userRepository.getUsersByRole(Role.USER);
            userList.remove(authenticatedUser);
            HashMap<User,Double> userDebMap = new HashMap<>();
            for(User user:userList){
                double deb = 0.0;
                List<Stock> userDebsList = stockService.getUserDebs(authenticatedUser);
                for (Stock stock: userDebsList){
                    if(paymentService
                            .getPaymentById(stock
                                    .getPayment()
                                    .getId())
                            .getOwner()
                            .equals(user)){
                        List<Stock> paymentSharersList = stockService.getPaymentById(stock.getPayment());
                        deb += stock.getPayment().getAmount() / paymentSharersList.size();
                    }
                }
                userDebsList = stockService.getUserDebs(user);
                for (Stock stock: userDebsList){
                    if(paymentService
                            .getPaymentById(stock
                                    .getPayment()
                                    .getId())
                            .getOwner()
                            .equals(authenticatedUser)){
                        List<Stock> paymentSharersList = stockService.getPaymentById(stock.getPayment());
                        deb -= stock.getPayment().getAmount() / paymentSharersList.size();
                    }
                }
                DecimalFormat decimalFormat = new DecimalFormat("###.##");
//                userDebMap.put(user,Double.parseDouble(decimalFormat.format(deb)));
                userDebMap.put(user,deb);
            }
            return userDebMap;
        }catch (NullPointerException e){
            return null;
        }
    }
}
