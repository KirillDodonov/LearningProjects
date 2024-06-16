package org.bankapp.operations;

public interface OperationCommandProcessor {
    public void processOperation();
    public ConsoleOperationType getOperationType();
}
