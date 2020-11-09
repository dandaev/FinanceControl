package com.example.financecontrol.service;

import com.example.financecontrol.domain.Role;
import com.example.financecontrol.domain.User;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    User getUserByUsername(String username);
    User getUserById(Integer id);
    boolean createUser(String fullname, String username, String password, Role role);
    User deleteUser(Integer id);
    boolean updateUser(User user);
    List<User> listAllUsers();
    List<User> listAllUsersWithRoleUser();
    HashMap<User,Double> getUserDebList(User user);
}
