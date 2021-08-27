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
    private HashMap<UUID, BigMoney> creditSplits;
    @Getter @Setter
    private HashMap<UUID,BigMoney> debitSplits;

    @Getter @Setter
    private UUID id;
    @Getter @Setter
    private LocalDate dateCreated;

    // To allow building by setter functions.
    public Transaction () {

    }

    public Transaction (CreateTransactionCommand command) {
        this.creditSplits = command.getAccountsCredited();
        this.debitSplits = command.getAccountsDebited();
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

            Set<UUID> accountCredited = creditSplits.keySet();
            for (UUID accountId : accountCredited) {
                BigMoney creditMoney = creditSplits.get(accountId);
                totalAmountCredited = totalAmountCredited.plus(creditMoney);
            }
            Set<UUID> accountDebited = debitSplits.keySet();
            for (UUID accountId : accountDebited) {
                BigMoney debitMoney = debitSplits.get(accountId);
                totalAmountDebited = totalAmountDebited.plus(debitMoney);
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
