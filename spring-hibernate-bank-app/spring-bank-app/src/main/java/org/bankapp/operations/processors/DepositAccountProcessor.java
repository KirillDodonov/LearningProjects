package org.bankapp.operations.processors;

import org.bankapp.account.AccountService;
import org.bankapp.operations.ConsoleOperationType;
import org.bankapp.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DepositAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final AccountService accountService;

    public DepositAccountProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter account Id: ");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to deposit: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        accountService.deposit(accountId, amount);
        System.out.println("Account with id %s replenished by %s$ successfully"
                .formatted(accountId, amount));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
