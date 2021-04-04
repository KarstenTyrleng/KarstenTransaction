package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.domain.Transaction;

import java.util.UUID;

public interface Repository {
    void saveTransaction(Transaction transaction);
    Transaction findTransaction(UUID transactionId);
}
