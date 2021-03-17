package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.domain.Transaction;
import io.github.tyrleng.karsten.transaction.domain.incoming.CreateTransactionCommand;
import io.github.tyrleng.karsten.transaction.domain.event.publish.TransactionCreatedEvent;
import io.github.tyrleng.karsten.transaction.domain.outgoing.Request;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TransactionService {

    Repository repository;
    EventPublisher eventPublisher;
    RequestPublisher requestPublisher;

    @Inject
    public TransactionService (Repository repository, EventPublisher eventPublisher, RequestPublisher requestPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.requestPublisher = requestPublisher;
    }

    public String createTransaction (CreateTransactionCommand command) {
        Transaction transaction = new Transaction(command);
        repository.saveTransaction(transaction);
        eventPublisher.publish(new TransactionCreatedEvent(transaction));
        return "ok";
    }
}
