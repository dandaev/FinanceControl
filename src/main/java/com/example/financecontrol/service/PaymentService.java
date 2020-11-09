package com.example.financecontrol.service;


import com.example.financecontrol.domain.Payment;
import com.example.financecontrol.domain.User;

import java.util.List;

public interface PaymentService {

    boolean createPayment(Payment payment);

    Payment getPaymentById(Integer paymentId);

    boolean updatePayment(Integer paymentId, Double amount, User owner, byte[] check);

    Payment deletePaymentById(Integer paymentId);

    List<Payment> getOwnerPayments(User owner);

}
