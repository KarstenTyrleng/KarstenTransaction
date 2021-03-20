package io.github.tyrleng.karsten.transaction.application.commands;

import io.github.tyrleng.karsten.transaction.domain.outgoing.Request;

public class GetAccountsCommand implements Request {

    @Override
    public boolean isCommand() {
        return true;
    }

    @Override
    public String getTopic() {
        return "getAccounts";
    }

    @Override
    public String getContents() {
        return null;
    }
}
