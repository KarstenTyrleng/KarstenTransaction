package io.github.tyrleng.karsten.transaction.domain.entity;

import io.github.tyrleng.finance.Money;

import java.util.ArrayList;

public class Account {

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

    private static ArrayList<Integer> accountCodeList;

    public static boolean checkAccountValid (int accountCode) {
        return false;
    }
}
