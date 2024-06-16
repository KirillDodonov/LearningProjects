package org.bankapp.operations.processors;

import org.bankapp.operations.ConsoleOperationType;
import org.bankapp.operations.OperationCommandProcessor;
import org.bankapp.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class ShowAllUserProcessor implements OperationCommandProcessor {
    private final UserService userService;

    public ShowAllUserProcessor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processOperation() {
        userService.getAllUsers().forEach(System.out::println);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}
