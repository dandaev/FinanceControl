package com.example.financecontrol.domain;

import javax.persistence.*;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Payment payment;

    @ManyToOne
    private User sharer;

    private boolean confirmation = false;

    public Stock() {
    }

    public Stock(Payment payment, User sharer) {
        this.payment = payment;
        this.sharer = sharer;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getSharer() {
        return sharer;
    }

    public void setSharer(User sharer) {
        this.sharer = sharer;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
