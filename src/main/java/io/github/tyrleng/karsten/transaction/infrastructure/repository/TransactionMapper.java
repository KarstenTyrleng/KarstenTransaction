package io.github.tyrleng.karsten.transaction.infrastructure.repository;

import io.github.tyrleng.karsten.transaction.domain.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionMapper {
    void createTransactionBase (Transaction transaction);
    void createTransactionSplit(TransactionSplit transactionSplit);
    List<TransactionSplit> getAllTransactionCreditSplit (UUID uuid);
    List<TransactionSplit> getAllTransactionDebitSplit(UUID uuid);
    Transaction getTransactionBase (UUID uuid);
    List<UUID> getAllTransactionId();
}
