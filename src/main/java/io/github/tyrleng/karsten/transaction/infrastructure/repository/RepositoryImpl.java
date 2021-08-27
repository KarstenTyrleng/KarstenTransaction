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
        HashMap<UUID, BigMoney> accountMoneyCredited = transaction.getCreditSplits();
        HashMap<UUID, BigMoney> accountMoneyDebited = transaction.getDebitSplits();

        Set<UUID> accountMoneyCreditedKeys = accountMoneyCredited.keySet();
        for (UUID accountId : accountMoneyCreditedKeys) {
                mapper.createTransactionSide(new TransactionSide(transaction.getId(), accountId, "CREDIT", accountMoneyCredited.get(accountId)));
        }

        Set<UUID> accountMoneyDebitedKeys = accountMoneyDebited.keySet();
        for (UUID accountId : accountMoneyDebitedKeys) {
            mapper.createTransactionSide(new TransactionSide(transaction.getId(), accountId, "DEBIT", accountMoneyDebited.get(accountId)));
        }
        session.commit();
        //TODO: wrap the whole code in transaction. Either everything goes well or all the DB entries roll back.
    }

    @Override
    public Transaction findTransaction(UUID transactionId) {
        SqlSession session = sqlSessionFactory.openSession();
        TransactionMapper mapper = session.getMapper(TransactionMapper.class);

        Transaction transaction = mapper.getTransactionBase(transactionId);

        List<TransactionSide> creditSideList = mapper.getAllTransactionCreditSide(transactionId);
        HashMap<UUID, BigMoney> accountMoneyCredited = new HashMap<>();
        for (TransactionSide transactionSide : creditSideList) {
            accountMoneyCredited.put(transactionSide.getAccountId(), transactionSide.getMoney());
        }

        List<TransactionSide> debitSideList = mapper.getAllTransactionDebitSide(transactionId);
        HashMap<UUID, BigMoney> accountMoneyDebited = new HashMap<>();
        for (TransactionSide transactionSide : debitSideList) {
            accountMoneyDebited.put(transactionSide.getAccountId(), transactionSide.getMoney());
        }

        transaction.setCreditSplits(accountMoneyCredited);
        transaction.setDebitSplits(accountMoneyDebited);

        return transaction;
    }

    @Override
    public List<UUID> getAllTransactionId() {
        SqlSession session = sqlSessionFactory.openSession();
        TransactionMapper mapper = session.getMapper(TransactionMapper.class);
        return mapper.getAllTransactionId();
    }

}
