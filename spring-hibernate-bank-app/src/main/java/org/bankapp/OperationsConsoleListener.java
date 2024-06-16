package org.bankapp;

import org.bankapp.operations.ConsoleOperationType;
import org.bankapp.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class OperationsConsoleListener {
    private final Scanner scanner;
    private final Map<ConsoleOperationType, OperationCommandProcessor> processorMap;

    public OperationsConsoleListener(
            Scanner scanner,
            List<OperationCommandProcessor> operationCommandProcessors
    ) {
        this.scanner = scanner;
        this.processorMap = operationCommandProcessors
                .stream()
                .collect(
                        Collectors.toMap(
                                OperationCommandProcessor::getOperationType,
                                processor -> processor
                        )
                );
    }

    public void listenUpdates() {
        while (true) {
            ConsoleOperationType nextOperation = listenNextOperation();
            processNextOperation(nextOperation);
        }
    }

    private ConsoleOperationType listenNextOperation() {
        System.out.println("Enter operation: ");
        printAllAvailableOperations();
        System.out.println();
        while (true) {
            String nextOperation = scanner.nextLine();
            try {
                return ConsoleOperationType.valueOf(nextOperation);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid operation: " + nextOperation);
            }
        }
    }

    private void printAllAvailableOperations() {
        processorMap.keySet().forEach(
                System.out::println);
    }

    private void processNextOperation(ConsoleOperationType operationType) {
        try {
            OperationCommandProcessor processor = processorMap.get(operationType);
            processor.processOperation();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
