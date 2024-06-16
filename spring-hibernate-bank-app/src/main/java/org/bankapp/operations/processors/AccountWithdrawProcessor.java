package org.bankapp.operations.processors;

import org.bankapp.account.AccountService;
import org.bankapp.operations.ConsoleOperationType;
import org.bankapp.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountWithdrawProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final AccountService accountService;

    public AccountWithdrawProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter account Id: ");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to withdraw: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        accountService.withdraw(accountId, amount);
        System.out.println("%s$ withdrawn from account with Id %s successfully"
                .formatted(amount, accountId));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }
}
