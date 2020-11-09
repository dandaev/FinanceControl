package com.example.financecontrol.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Timestamp date;
    private Double amount;
    @ManyToOne
    private User owner;
    @Lob
    private byte[] cheque;

    public Payment(Double amount, User owner, byte[] cheque) {
        this.date = new Timestamp(System.currentTimeMillis());
        this.amount = amount;
        this.owner = owner;
        this.cheque = cheque;
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

    public byte[] getCheque() {
        return cheque;
    }

    public void setCheque(byte[] cheque) {
        this.cheque = cheque;
    }
}
