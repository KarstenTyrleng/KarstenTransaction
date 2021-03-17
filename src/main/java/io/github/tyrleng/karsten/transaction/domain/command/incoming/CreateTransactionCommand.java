package io.github.tyrleng.karsten.transaction.domain.command.incoming;

import io.github.tyrleng.finance.Money;
import io.github.tyrleng.karsten.transaction.domain.entity.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;

public class CreateTransactionCommand {

    @Getter
    private HashMap<Account,Money> accountsCredited;
    @Getter
    private  HashMap<Account,Money> accountsDebited;

    public CreateTransactionCommand () {
        this.accountsCredited = new HashMap<>();
        this.accountsDebited = new HashMap<>();
    }
    
    public void addCreditAccountAndAmount(Account account, Money amount) {
        accountsCredited.put(account,amount);
    }

    public void addDebitAccountAndAmount (Account account, Money amount) {
        accountsDebited.put(account,amount);
    }

}
