package com.example.financecontrol.repository;

import com.example.financecontrol.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User getUserByUsername(String username);

    User getUserById(Integer id);

}
