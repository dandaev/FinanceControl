package com.example.financecontrol.service.implementations;

import com.example.financecontrol.domain.Role;
import com.example.financecontrol.domain.User;
import com.example.financecontrol.repository.UserRepository;
import com.example.financecontrol.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
