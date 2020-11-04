package com.example.financecontrol.domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Timestamp date;
    private Double amount;
    @ManyToOne
    private User owner;
    private String check;

    public Payment(Double amount, User owner, String check) {
        this.date = new Timestamp(System.currentTimeMillis());
        this.amount = amount;
        this.owner = owner;
        this.check = check;
    }

    public Payment() {
    }

    public Integer getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
