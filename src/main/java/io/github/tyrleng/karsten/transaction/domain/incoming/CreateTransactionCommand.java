package io.github.tyrleng.karsten.transaction.domain.incoming;

import lombok.Getter;
import org.joda.money.BigMoney;

import java.util.HashMap;
import java.util.UUID;

public class CreateTransactionCommand {

    @Getter
    private HashMap<UUID, BigMoney> accountsCredited;
    @Getter
    private HashMap<UUID,BigMoney> accountsDebited;

    public CreateTransactionCommand () {
        this.accountsCredited = new HashMap<>();
        this.accountsDebited = new HashMap<>();
    }
    
    public void addCreditAccountAndAmount(UUID account, BigMoney amount) {
        accountsCredited.put(account,amount);
    }

    public void addDebitAccountAndAmount (UUID account, BigMoney amount) {
        accountsDebited.put(account,amount);
    }

}
