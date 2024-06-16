package org.bankapp.account;

import org.bankapp.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {
    private int accountIdCounter;
    private final Map<Integer, Account> accountMap;
    private final AccountProperties accountProperties;
    private final SessionFactory factory;

    public AccountService(AccountProperties accountProperties, SessionFactory factory) {
        this.accountMap = new HashMap<Integer, Account>();
        this.accountProperties = accountProperties;
        this.factory = factory;
    }

    public Account createAccount(User user) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Account newAccount = new Account(user, 10.0);
            session.save(newAccount);
            session.refresh(user);
            session.getTransaction().commit();
            return newAccount;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Optional<Account> findAccountById(int id) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            var account = session.get(Account.class, id);
            return Optional.ofNullable(account);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void withdraw(int accountId, double amount) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Account account = checkAccountValidity(accountId);
            if (account.getBalance() < amount) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            account.setBalance(account.getBalance() - amount);
            session.merge(account);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void deposit(int accountId, double amount) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Account account = checkAccountValidity(accountId);
            account.setBalance(account.getBalance() + amount);
            session.merge(account);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Account closeAccount(int accountId) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Account account = checkAccountValidity(accountId);
            if (account.getBalance() != 0){
            throw new IllegalArgumentException("Сannot close the account with a balance");
            }
            session.delete(account);
            User user = account.getUser();
            session.refresh(user);
            session.getTransaction().commit();
            return account;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void transfer(int sourceAccountId, int targetAccountId, double amount) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Account sourceAccount = checkAccountValidity(sourceAccountId);
            Account targetAccount = checkAccountValidity(targetAccountId);
            if (sourceAccount.getBalance() < amount) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);

            double totalAmount = sourceAccount.getUser().getId() != targetAccount.getUser().getId()
                    ? amount * (1 - accountProperties.getTransferFee())
                    : amount;
            targetAccount.setBalance(targetAccount.getBalance() + totalAmount);
            session.merge(sourceAccount);
            session.merge(targetAccount);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Account checkAccountValidity(int accountId) {
        return findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with id %s not found"
                        .formatted(accountId)));
    }
}
