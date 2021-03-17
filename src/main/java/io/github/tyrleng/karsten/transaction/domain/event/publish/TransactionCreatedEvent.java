package io.github.tyrleng.karsten.transaction.domain.event.publish;

import io.github.tyrleng.karsten.transaction.domain.Transaction;
import lombok.Getter;

public class TransactionCreatedEvent extends BasePublishEvent{

    @Getter
    Transaction transaction;

    public TransactionCreatedEvent (Transaction transaction) {
        this.transaction = transaction;
    }
}
