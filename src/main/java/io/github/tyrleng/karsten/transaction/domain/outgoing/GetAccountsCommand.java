package io.github.tyrleng.karsten.transaction.domain.outgoing;

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
