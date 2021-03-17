package io.github.tyrleng.karsten.transaction.infrastructure;

import io.github.tyrleng.karsten.transaction.application.Repository;
import io.github.tyrleng.karsten.transaction.domain.Transaction;

import javax.inject.Inject;

public class RepositoryImpl implements Repository {

    @Inject
    public RepositoryImpl() {

    }

    @Override
    public void saveTransaction(Transaction transaction) {

    }
}
