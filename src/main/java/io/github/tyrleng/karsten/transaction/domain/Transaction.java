package io.github.tyrleng.karsten.transaction.domain;

import io.github.tyrleng.karsten.transaction.domain.incoming.CreateTransactionCommand;
import lombok.Getter;
import lombok.Setter;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Main Aggregate of this Bounded Context.
 * Remember you can have pure foreign currency transactions, or mixed currency transactions.
 */
public class Transaction {

    @Getter @Setter
    private HashMap<UUID, BigMoney> accountMoneyCredited;
    @Getter @Setter
    private HashMap<UUID,BigMoney> accountMoneyDebited;

    @Getter @Setter
    private UUID id;
    @Getter @Setter
    private LocalDate dateCreated;

    public Transaction () {

    }

    public Transaction (CreateTransactionCommand command) {
        this.accountMoneyCredited = command.getAccountsCredited();
        this.accountMoneyDebited = command.getAccountsDebited();
        checkAccountsValid();
        checkCreditDebitAmountsBalance();
        id = UUID.randomUUID();
        dateCreated = LocalDate.now();
    }

    private void checkAccountsValid () {

    }

    private void checkCreditDebitAmountsBalance () {
        try {
            CurrencyUnit sgd = CurrencyUnit.of("SGD");
            BigMoney totalAmountCredited = BigMoney.of(sgd, 0);
            BigMoney totalAmountDebited = BigMoney.of(sgd, 0);

            Set<UUID> accountCredited = accountMoneyCredited.keySet();
            for (UUID account : accountCredited) {
                BigMoney amount = accountMoneyCredited.get(account);
                totalAmountCredited = totalAmountCredited.plus(amount);
            }
            Set<UUID> accountDebited = accountMoneyDebited.keySet();
            for (UUID account : accountDebited) {
                BigMoney amount = accountMoneyDebited.get(account);
                totalAmountDebited = totalAmountDebited.plus(amount);
            }
            if (!totalAmountCredited.equals(totalAmountDebited)) {
                throw new TransactionException();
            }
        }
        catch (TransactionException e) {
            e.printStackTrace();
        }
    }
}
