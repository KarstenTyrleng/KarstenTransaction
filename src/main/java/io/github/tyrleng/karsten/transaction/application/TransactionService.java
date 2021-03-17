package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.domain.Transaction;
import io.github.tyrleng.karsten.transaction.domain.command.incoming.CreateTransactionCommand;
import io.github.tyrleng.karsten.transaction.domain.event.publish.TransactionCreatedEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;

@Singleton
public class TransactionService {

    Repository repository;
    EventPublisher eventPublisher;
    Requester requester;

    @Inject
    public TransactionService (Repository repository, EventPublisher eventPublisher, Requester requester) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.requester = requester;
    }

    public String createTransaction (CreateTransactionCommand command) {
        Transaction transaction = new Transaction(command);
        repository.saveTransaction(transaction);
        eventPublisher.publish(new TransactionCreatedEvent(transaction));
        return "ok";
    }
}
