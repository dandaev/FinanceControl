package com.example.financecontrol.repository;

import com.example.financecontrol.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PaymentRepository extends JpaRepository< Payment, Integer > {

    Payment getPaymentById(Integer paymentId);

    List<Payment> getAllByOwnerId(Integer ownerId);
}
