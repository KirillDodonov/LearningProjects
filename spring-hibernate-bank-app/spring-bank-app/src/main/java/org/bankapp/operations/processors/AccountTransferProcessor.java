package org.bankapp.operations.processors;

import org.bankapp.account.AccountService;
import org.bankapp.operations.ConsoleOperationType;
import org.bankapp.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountTransferProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final AccountService accountService;

    public AccountTransferProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter source account Id: ");
        int sourceAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter target account Id: ");
        int targetAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to transfer: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        accountService.transfer(sourceAccountId, targetAccountId, amount);
        System.out.println("Successfully transferred %s from accountId %s to accountId %s"
                .formatted(amount, sourceAccountId, targetAccountId));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
