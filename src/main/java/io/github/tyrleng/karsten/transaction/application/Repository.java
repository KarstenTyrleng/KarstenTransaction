package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.domain.Transaction;

public interface Repository {
    public void saveTransaction(Transaction transaction);
}
