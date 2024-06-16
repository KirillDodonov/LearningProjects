package org.bankapp.account;

import jakarta.persistence.*;
import org.bankapp.user.User;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Column(name = "balance")
    private double balance;

    public Account() {
    }

    public Account(User user, double balance) {
        this.user = user;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + id +
                ", userId=" + user +
                ", balance=" + balance +
                '}';
    }
}
