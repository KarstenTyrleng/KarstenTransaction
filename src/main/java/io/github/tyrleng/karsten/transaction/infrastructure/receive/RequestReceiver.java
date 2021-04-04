package io.github.tyrleng.karsten.transaction.infrastructure.receive;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.tyrleng.finance.Money;
import io.github.tyrleng.karsten.transaction.application.TransactionService;
import io.github.tyrleng.karsten.transaction.domain.incoming.CreateTransactionCommand;
import io.github.tyrleng.karsten.transaction.domain.entity.Account;

import javax.inject.Inject;
import java.io.IOException;
import java.io.StringWriter;

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
            JsonNode requestTypeNode = rootNode.get("requestType");
            String requestType = requestTypeNode.asText();

            switch (requestType) {
                case "query": {
                    return handleQuery(rootNode);
                }
                case "command": {
                    handleCommand(rootNode);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return " ";
    }

    private String handleQuery (JsonNode rootNode){
       return null;
    }

    private String handleCommand (JsonNode rootNode) {
        JsonNode topicNode = rootNode.get("topic");
        String topic = topicNode.asText();

        JsonNode requestIdNode = rootNode.get("requestId");
        int requestId = requestIdNode.asInt();

        switch (topic) {
            case "createTransaction" : {
                CreateTransactionCommand command = createTransactionCommand(rootNode);
                transactionService.createTransaction(command);
                return createTransactionJson(requestId, topic);
            }
        }
        return "Receiver's Handle Command Fucked Up";
    }

    private CreateTransactionCommand createTransactionCommand(JsonNode rootNode) {
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
        return command;
    }

    private String createTransactionJson (int requestId, String topic) {
        try {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(writer);
            jsonGenerator.useDefaultPrettyPrinter();

            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("requestId", requestId);
            jsonGenerator.writeStringField("topic", topic);

            jsonGenerator.writeEndObject();
            jsonGenerator.close();
            return writer.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }
}
