package io.github.tyrleng.karsten.transaction.infrastructure.repository;

import io.github.tyrleng.karsten.transaction.domain.Transaction;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TransactionMapper {
    void createTransactionBase (Transaction transaction);
    void createTransactionSide (TransactionSide transactionSide);
    List<TransactionSide> getAllTransactionCreditSide (UUID uuid);
    List<TransactionSide> getAllTransactionDebitSide (UUID uuid);
    Transaction getTransactionBase (UUID uuid);
    List<UUID> getAllTransactionId();
}
