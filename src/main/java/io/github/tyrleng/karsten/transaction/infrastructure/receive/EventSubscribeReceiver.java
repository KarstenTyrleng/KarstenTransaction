package io.github.tyrleng.karsten.transaction.infrastructure.receive;


import io.github.tyrleng.karsten.transaction.application.TransactionService;

import javax.inject.Inject;
import java.util.Stack;

public class EventSubscribeReceiver {
    // Based on subscription events read from domain, add to stack

    TransactionService transactionService;

    @Inject
    public EventSubscribeReceiver(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Stack<String> topicsToSubscribeTo () {
        return new Stack<>();
    }

    public void handleMessage (String topic, String content) {

    }
}
