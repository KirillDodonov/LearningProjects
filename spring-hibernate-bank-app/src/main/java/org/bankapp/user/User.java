package org.bankapp.user;

import jakarta.persistence.*;
import org.bankapp.account.Account;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"login"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "login")
    private String login;
    @OneToMany(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.REFRESH)
    private List<Account> accountList;

    public User() {
    }

    public User(String login, List<Account> accountList) {
        this.login = login;
        this.accountList = accountList;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
