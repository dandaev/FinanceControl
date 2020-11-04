package com.example.financecontrol.service.implementations;

import com.example.financecontrol.domain.Payment;
import com.example.financecontrol.domain.User;
import com.example.financecontrol.repository.PaymentRepository;
import com.example.financecontrol.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public boolean createPayment(Double amount, User owner, String check) {
        Payment payment = new Payment(amount, owner, check);
        paymentRepository.save(payment);
        return true;
    }

    @Override
    public Payment getPaymentById(Integer paymentId) {
        Payment payment = paymentRepository.getPaymentById(paymentId);
        if (payment == null){
            return null;
        }
        return payment;
    }

    @Override
    public boolean updatePayment(Integer paymentId, Double amount, User owner, String check) {
        Payment payment = paymentRepository.getPaymentById(paymentId);
        if (payment == null){
            return false;
        }
        payment.setAmount(amount);
        payment.setOwner(owner);
        payment.setCheck(check);
        paymentRepository.save(payment);
        return true;
    }

    @Override
    public Payment deletePaymentById(Integer paymentId) {
        Payment payment = paymentRepository.getPaymentById(paymentId);
        if (payment == null){
            return null;
        }
        paymentRepository.delete(payment);
        return payment;
    }

    @Override
    public List<Payment> getOwnerPayments(User owner) {
        List<Payment> paymentList = paymentRepository.getAllByOwnerId(owner.getId());
        if (paymentList.isEmpty()){
            return null;
        }
        return paymentList;
    }
}
