package io.github.tyrleng.karsten.transaction.domain.entity;

import io.github.tyrleng.finance.Money;

import java.util.ArrayList;
import java.util.HashMap;

public class Account {

    public Account (int accountCode) {
        this.accountCode = accountCode;
    }

    int accountCode;

    Money amountToBeDebited;
    Money amountToBeCredited;

    private static ArrayList<Integer> accountCodeList;

    public static boolean checkAccountValid (int accountCode) {
        return false;
    }
}
