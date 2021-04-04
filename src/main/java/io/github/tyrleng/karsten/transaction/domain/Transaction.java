package io.github.tyrleng.karsten.transaction.domain;

import io.github.tyrleng.finance.Money;
import io.github.tyrleng.karsten.transaction.domain.incoming.CreateTransactionCommand;
import io.github.tyrleng.karsten.transaction.domain.entity.Account;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Main Aggregate of this Bounded Context.
 * Remember you can have pure foreign currency transactions, or mixed currency transactions.
 */
public class Transaction {

    @Getter private HashMap<Account,Money> accountMoneyCredited;
    @Getter private HashMap<Account,Money> accountMoneyDebited;

    @Getter private UUID id;
    @Getter private LocalDate dateCreated;

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
            Money totalAmountCredited = new Money("SGD", "0");
            Money totalAmountDebited = new Money("SGD", "0");

            Set<Account> accountCredited = accountMoneyCredited.keySet();
            for (Account account : accountCredited) {
                Money amount = accountMoneyCredited.get(account);
                totalAmountCredited = totalAmountCredited.add(amount);
            }
            Set<Account> accountDebited = accountMoneyDebited.keySet();
            for (Account account : accountDebited) {
                Money amount = accountMoneyDebited.get(account);
                totalAmountDebited = totalAmountDebited.add(amount);
            }
            if (!totalAmountCredited.equals(totalAmountDebited)) {
                throw new TransactionException();
            }
        }
        catch (TransactionException e) {
            e.printStackTrace();;
        }
    }
}
