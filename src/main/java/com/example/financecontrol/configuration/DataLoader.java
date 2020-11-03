package com.example.financecontrol.configuration;

import com.example.financecontrol.domain.Role;
import com.example.financecontrol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DataLoader implements ApplicationRunner {

    private UserService userService;
    private WebSecurityConfig webSecurityConfig;

    @Autowired
    public DataLoader(
            UserService userService,
            WebSecurityConfig webSecurityConfig) {
        this.userService = userService;
        this.webSecurityConfig = webSecurityConfig;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.createUser(
                "Super Admin",
                "admin",
                webSecurityConfig.passwordEncoder().encode("123"),
                Role.ADMIN
        );
        userService.createUser("Atai Isaev",
                "isaev",
                webSecurityConfig.passwordEncoder().encode("123"),
                Role.USER
        );
        userService.createUser("Alybek Dandaev",
                "dandaev",
                webSecurityConfig.passwordEncoder().encode("123"),
                Role.USER
        );
        userService.createUser("Bekzhan Satarov",
                "satarov",
                webSecurityConfig.passwordEncoder().encode("123"),
                Role.USER
        );
        userService.createUser("Bekbolot Nurmanbetov",
                "nurmanbetov",
                webSecurityConfig.passwordEncoder().encode("123"),
                Role.USER
        );
    }
}
