package org.bankapp.operations.processors;

import org.bankapp.operations.ConsoleOperationType;
import org.bankapp.operations.OperationCommandProcessor;
import org.bankapp.user.User;
import org.bankapp.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateUserProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final UserService userService;

    public CreateUserProcessor(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter login: ");
        String login = scanner.nextLine();
        User user = userService.createUser(login);
        System.out.println("User with Id \"%s\" created: "
                .formatted(login) + user.toString());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
