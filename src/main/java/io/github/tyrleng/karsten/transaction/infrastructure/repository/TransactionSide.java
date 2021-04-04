package io.github.tyrleng.karsten.transaction.infrastructure.repository;

import lombok.Getter;
import lombok.Setter;
import org.joda.money.BigMoney;

import java.util.UUID;

public class TransactionSide {
    @Getter @Setter
    private UUID accountId;
    @Getter @Setter
    private BigMoney money;
}
