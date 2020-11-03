package com.example.financecontrol.configuration;

import com.example.financecontrol.domain.Role;
import com.example.financecontrol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(
            UserService userService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.createUser(
                "Super Admin",
                "admin",
                passwordEncoder.encode("123"),
                Role.ADMIN
        );
        userService.createUser(
                "Atai Isaev",
                "isaev",
                passwordEncoder.encode("123"),
                Role.USER
        );
        userService.createUser(
                "Alybek Dandaev",
                "dandaev",
                passwordEncoder.encode("123"),
                Role.USER
        );
        userService.createUser(
                "Bekzhan Satarov",
                "satarov",
                passwordEncoder.encode("123"),
                Role.USER
        );
        userService.createUser(
                "Bekbolot Nurmanbetov",
                "nurmanbetov",
                passwordEncoder.encode("123"),
                Role.USER
        );
    }
}
