package org.bankapp.user;

import org.bankapp.account.Account;
import org.bankapp.account.AccountService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final Map<Integer, User> userMap;
    private final AccountService accountService;
    private final SessionFactory factory;

    public UserService(AccountService accountService, SessionFactory factory) {
        this.accountService = accountService;
        this.factory = factory;
        this.userMap = new HashMap<>();
    }

    public User createUser(String login) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            if (login.length() < 2) {
                throw new IllegalArgumentException("Login must be at least 2 characters");
            }
            User newUser = new User(login, new ArrayList<>());
            session.save(newUser);
            session.getTransaction().commit();
            accountService.createAccount(newUser);
            return newUser;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Optional<User> findUserById(int userId) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            return Optional.ofNullable(session.get(User.class, userId));
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public List<User> getAllUsers() {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            List<User> users = session.createQuery("from User").getResultList();
            if (users.isEmpty()) {
                throw new IllegalArgumentException("No users created");
            }
            return users;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public User checkUserValidity(int userId) {
        return findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with Id %s not found"
                        .formatted(userId)));
    }
}
