package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.domain.Transaction;

public interface Repository {
    void saveTransaction(Transaction transaction);
}
