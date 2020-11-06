package com.example.financecontrol.service;

import com.example.financecontrol.domain.Role;
import com.example.financecontrol.domain.User;

import java.util.List;

public interface UserService {
    public User getUserByUsername(String username);
    public boolean createUser(String fullname, String username, String password, Role role);
    public User deleteUser(Integer id);
    public boolean updateUser(User user);
    List<User> listAllUsers();
    User getUserById(Integer id);
}
