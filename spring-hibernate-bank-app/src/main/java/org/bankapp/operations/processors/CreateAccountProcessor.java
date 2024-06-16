package org.bankapp.operations.processors;

import org.bankapp.account.Account;
import org.bankapp.account.AccountService;
import org.bankapp.operations.ConsoleOperationType;
import org.bankapp.operations.OperationCommandProcessor;
import org.bankapp.user.User;
import org.bankapp.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final AccountService accountService;
    private final UserService userService;

    public CreateAccountProcessor(Scanner scanner, AccountService accountService, UserService userService) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter user Id: ");
        int userId = Integer.parseInt(scanner.nextLine());
        User user = userService.checkUserValidity(userId);
        Account account = accountService.createAccount(user);
        System.out.println("Account with id %s for user with Id %s created successfully"
                .formatted(account.getId(), user.getId()) + account.toString());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
