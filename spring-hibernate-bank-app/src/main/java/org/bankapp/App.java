package org.bankapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                "org.bankapp"
        );
        OperationsConsoleListener consoleListener = context.getBean(OperationsConsoleListener.class);

        consoleListener.listenUpdates();
        context.close();
    }
}
