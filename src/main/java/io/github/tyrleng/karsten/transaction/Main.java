package io.github.tyrleng.karsten.transaction;

import io.github.tyrleng.karsten.transaction.application.TransactionService;
import io.github.tyrleng.karsten.transaction.infrastructure.MessageLoop;

public class Main {
    public static void main (String[] args) {
        MessageLoopFactory factory = DaggerMessageLoopFactory.create();
        MessageLoop messageLoop = factory.createMessageLoop();
        messageLoop.enterMainLoop();
    }
}
