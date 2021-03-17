package io.github.tyrleng.karsten.transaction.infrastructure.receive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.tyrleng.finance.Money;
import io.github.tyrleng.karsten.transaction.application.TransactionService;
import io.github.tyrleng.karsten.transaction.domain.incoming.CreateTransactionCommand;
import io.github.tyrleng.karsten.transaction.domain.entity.Account;

import javax.inject.Inject;

/**
 * Reads Json and parses the Json into a command / query.
 * Will need knowledge of the incoming commands / queries that the domain accepts.
 * Will need to know which ApplicationService method to use for forwarding the command / query.
 * So its a heavily connected class. Will be easy to break.
 */

public class RequestReceiver {
    // check if request is query or command
    // format request out of json into java class
    // forward java class to ApplicationService

    private TransactionService transactionService;

    @Inject
    RequestReceiver(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public String handleRequest(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(message);

            String commandString = rootNode.get("command").asText();
            switch (commandString) {
                case "createTransaction":
                    CreateTransactionCommand command =  new CreateTransactionCommand();
                    ArrayNode creditArray = (ArrayNode)rootNode.get("credit");
                    ArrayNode debitArray = (ArrayNode)rootNode.get("debit");
                    for (JsonNode creditAccountAndAmount : creditArray) {
                        Account account = new Account(creditAccountAndAmount.get("account").asInt());
                        JsonNode moneyJsonNode = creditAccountAndAmount.get("money");
                        Money amount = new Money(moneyJsonNode.get("currencyCode").asText(), moneyJsonNode.get("amount").asText());
                        command.addCreditAccountAndAmount(account,amount);
                    }
                    for (JsonNode debitAccountAndAmount : debitArray) {
                        Account account = new Account(debitAccountAndAmount.get("account").asInt());
                        JsonNode moneyJsonNode = debitAccountAndAmount.get("money");
                        Money amount = new Money(moneyJsonNode.get("currencyCode").asText(), moneyJsonNode.get("amount").asText());
                        command.addDebitAccountAndAmount(account,amount);
                    }
                    return transactionService.createTransaction(command);
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "incoming request error";
    }
}
