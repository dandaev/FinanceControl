package com.example.financecontrol.repository;

import com.example.financecontrol.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User getUserByUsername(String username);
    public User getUserById(Integer id);
}
