package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.application.commands.GetAccountsCommand;
import io.github.tyrleng.karsten.transaction.domain.Transaction;
import io.github.tyrleng.karsten.transaction.domain.incoming.CreateTransactionCommand;
import io.github.tyrleng.karsten.transaction.domain.event.publish.TransactionCreatedEvent;
import io.github.tyrleng.karsten.transaction.domain.outgoing.Request;
import io.github.tyrleng.karsten.transaction.domain.outgoing.RequestServicer;
import lombok.Setter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Singleton
public class TransactionService {

    Repository repository;
    EventPublisher eventPublisher;
    RequestPublisher requestPublisher;
    RequestServicerImpl requestServicer;

    @Inject
    public TransactionService (Repository repository, EventPublisher eventPublisher, RequestPublisher requestPublisher, RequestServicerImpl requestServicer) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.requestPublisher = requestPublisher;
        this.requestServicer = requestServicer;
//        getAccounts();
    }

    public UUID createTransaction (CreateTransactionCommand command) {
        Transaction transaction = new Transaction(command);
        repository.saveTransaction(transaction);
        eventPublisher.publish(new TransactionCreatedEvent(transaction));
        return transaction.getId();
    }

    public List<UUID> getTransactionIds () {
        return repository.getAllTransactionId();
    }

    @Setter
    private static ArrayList<UUID> accountIdList;
    /**
     * Does the population of Account IDs for accounts.
     * Has to be done here because making Account do the population by itself would be awkward.
     */
    private void getAccounts () {
        requestServicer.makeGetAccountIdRequest(new GetAccountsCommand(), handleCommandResults);
    }

    private final Consumer<ArrayList<UUID>> handleCommandResults = TransactionService::setAccountIdList;
}
