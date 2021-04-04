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

    }

    @Override
    public Transaction findTransaction(UUID transactionId) {
        SqlSession session = sqlSessionFactory.openSession();
        TransactionMapper mapper = session.getMapper(TransactionMapper.class);

        Transaction transaction = mapper.getTransaction(transactionId);

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

        transaction.setAccountMoneyCredited(accountMoneyCredited);
        transaction.setAccountMoneyDebited(accountMoneyDebited);

        return transaction;
    }
}
