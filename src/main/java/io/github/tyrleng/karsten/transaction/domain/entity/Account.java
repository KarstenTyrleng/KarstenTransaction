package io.github.tyrleng.karsten.transaction.domain.entity;

import io.github.tyrleng.finance.Money;
import io.github.tyrleng.karsten.transaction.application.commands.GetAccountsCommand;
import io.github.tyrleng.karsten.transaction.domain.outgoing.RequestServicer;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class Account {

    @Inject
    public Account (int accountCode, String accountName, AccountType accountType) {
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.accountType = accountType;
    }

    int accountCode;
    String accountName;
    AccountType accountType;

    Money amountToBeDebited;
    Money amountToBeCredited;

    @Getter
    @Setter
    private static ArrayList<Integer> accountIdList;

    public static boolean checkAccountValid (int accountCode) {
        return false;
    }
}
