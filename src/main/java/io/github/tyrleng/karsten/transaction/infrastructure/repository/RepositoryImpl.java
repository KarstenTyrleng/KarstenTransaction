package io.github.tyrleng.karsten.transaction.infrastructure.repository;

import io.github.tyrleng.karsten.transaction.application.Repository;
import io.github.tyrleng.karsten.transaction.domain.Transaction;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.joda.money.BigMoney;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class RepositoryImpl implements Repository {

    private static final SqlSessionFactory sqlSessionFactory;

    static {
        try
        {
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e)
        {
            throw new RuntimeException(e.getCause());
        }
    }

    @Inject
    public RepositoryImpl() {

    }

    @Override
    public void saveTransaction(Transaction transaction) {
        SqlSession session = sqlSessionFactory.openSession();
        TransactionMapper mapper = session.getMapper(TransactionMapper.class);
        mapper.createTransactionBase(transaction);
        HashMap<UUID, BigMoney> creditSplits = transaction.getCreditSplits();
        HashMap<UUID, BigMoney> debitSplits = transaction.getDebitSplits();

        Set<UUID> accountCreditIdKeys = creditSplits.keySet();
        for (UUID accountId : accountCreditIdKeys) {
                mapper.createTransactionSplit(new TransactionSplit(transaction.getId(), accountId, "CREDIT", creditSplits.get(accountId)));
        }

        Set<UUID> accountDebitIdKeys = debitSplits.keySet();
        for (UUID accountId : accountDebitIdKeys) {
            mapper.createTransactionSplit(new TransactionSplit(transaction.getId(), accountId, "DEBIT", debitSplits.get(accountId)));
        }
        session.commit();
        //TODO: wrap the whole code in transaction. Either everything goes well or all the DB entries roll back.
    }

    @Override
    public Transaction findTransaction(UUID transactionId) {
        SqlSession session = sqlSessionFactory.openSession();
        TransactionMapper mapper = session.getMapper(TransactionMapper.class);

        Transaction transaction = mapper.getTransactionBase(transactionId);

        List<TransactionSplit> creditSplitList = mapper.getAllTransactionCreditSplit(transactionId);
        HashMap<UUID, BigMoney> creditSplits = new HashMap<>();
        for (TransactionSplit transactionSplit : creditSplitList) {
            creditSplits.put(transactionSplit.getAccountId(), transactionSplit.getMoney());
        }

        List<TransactionSplit> debitSplitList = mapper.getAllTransactionDebitSplit(transactionId);
        HashMap<UUID, BigMoney> debitSplits = new HashMap<>();
        for (TransactionSplit transactionSplit : debitSplitList) {
            debitSplits.put(transactionSplit.getAccountId(), transactionSplit.getMoney());
        }

        transaction.setCreditSplits(creditSplits);
        transaction.setDebitSplits(debitSplits);

        return transaction;
    }

    @Override
    public List<UUID> getAllTransactionId() {
        SqlSession session = sqlSessionFactory.openSession();
        TransactionMapper mapper = session.getMapper(TransactionMapper.class);
        return mapper.getAllTransactionId();
    }

}
