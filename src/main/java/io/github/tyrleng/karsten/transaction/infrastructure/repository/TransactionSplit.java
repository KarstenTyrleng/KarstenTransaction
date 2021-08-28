package io.github.tyrleng.karsten.transaction.infrastructure.repository;

import lombok.Getter;
import lombok.Setter;
import org.joda.money.BigMoney;

import java.util.UUID;

public class TransactionSplit {
    @Getter @Setter
    private UUID accountId;
    @Getter @Setter
    private BigMoney money;
    @Getter @Setter
    private UUID transactionId;
    @Getter @Setter
    private String transactionType;

    TransactionSplit() {

    }

    TransactionSplit(UUID transactionId, UUID accountId, String transactionType, BigMoney money) {
        this.accountId = accountId;
        this.money = money;
        this.transactionId = transactionId;
        this.transactionType = transactionType;
    }
}
