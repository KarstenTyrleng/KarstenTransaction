package io.github.tyrleng.karsten.transaction.infrastructure.receive;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.tyrleng.karsten.transaction.application.TransactionService;
import io.github.tyrleng.karsten.transaction.domain.Transaction;
import io.github.tyrleng.karsten.transaction.domain.incoming.CreateTransactionCommand;
import io.github.tyrleng.karsten.transaction.infrastructure.domainConverter.TransactionConverter;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import javax.inject.Inject;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
                    return handleCommand(rootNode);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return " ";
    }

    private String handleQuery (JsonNode rootNode){
        JsonNode topicNode = rootNode.get("topic");
        String topic = topicNode.asText();

        JsonNode requestIdNode = rootNode.get("requestId");
        int requestId = requestIdNode.asInt();

        switch (topic) {
            case "getAllTransactionId": {
                List<UUID> transactionIdList = transactionService.getTransactionIds();
                return getAllTransactionIdJsonReply(transactionIdList, requestId, topic);
            }
            case "getTransaction": {
                JsonNode transactionIdNode = rootNode.get("transactionId");
                UUID transactionId = UUID.fromString(transactionIdNode.textValue());
                Transaction transaction = transactionService.findTransaction(transactionId);
                return getTransactionJsonReply(transaction, requestId, topic);
            }
            default:
                return "You Fucked Up";
        }
    }

    private String getAllTransactionIdJsonReply(List<UUID> transactionIdList, int requestId, String topic) {
        try {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(writer);
            jsonGenerator.useDefaultPrettyPrinter();

            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("requestId", requestId);
            jsonGenerator.writeStringField("topic", topic);
            jsonGenerator.writeFieldName("transactionIdList");
            jsonGenerator.writeStartArray();
            for (UUID accountId : transactionIdList) {
                jsonGenerator.writeString(accountId.toString());
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
            jsonGenerator.close();
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // by right, should be some status code.
        return " ";
    }

    private String getTransactionJsonReply (Transaction transaction, int requestId, String topic) {
        try {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(writer);
            jsonGenerator.useDefaultPrettyPrinter();

            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("requestId", requestId);
            jsonGenerator.writeStringField("topic", topic);
            String transactionJson = new TransactionConverter(transaction).provideDomainObjectAsJson();
            jsonGenerator.writeRaw(transactionJson);
            jsonGenerator.close();
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // by right, should be some status code.
        return " ";
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
                return createTransactionJsonReply(requestId, topic);
            }
        }
        return "Receiver's Handle Command Fucked Up";
    }

    private CreateTransactionCommand createTransactionCommand(JsonNode rootNode) {
        CreateTransactionCommand command =  new CreateTransactionCommand();
        ArrayNode creditArray = (ArrayNode)rootNode.get("credit");
        ArrayNode debitArray = (ArrayNode)rootNode.get("debit");

        for (JsonNode creditAccountAndAmount : creditArray) {
            UUID accountId = UUID.fromString(creditAccountAndAmount.get("accountId").asText()) ;
            JsonNode moneyJsonNode = creditAccountAndAmount.get("money");
            CurrencyUnit currencyUnit = CurrencyUnit.of(moneyJsonNode.get("currencyCode").asText());
            BigDecimal amount = new BigDecimal( moneyJsonNode.get("amount").asText());
            BigMoney creditAmount = BigMoney.of(currencyUnit, amount);
            command.addCreditAccountAndAmount(accountId,creditAmount);
        }
        for (JsonNode debitAccountAndAmount : debitArray) {
            UUID accountId = UUID.fromString(debitAccountAndAmount.get("accountId").asText()) ;
            JsonNode moneyJsonNode = debitAccountAndAmount.get("money");
            CurrencyUnit currencyUnit = CurrencyUnit.of(moneyJsonNode.get("currencyCode").asText());
            BigDecimal amount = new BigDecimal( moneyJsonNode.get("amount").asText());
            BigMoney debitAmount = BigMoney.of(currencyUnit, amount);
            command.addDebitAccountAndAmount(accountId,debitAmount);
        }
        return command;
    }

    private String createTransactionJsonReply (int requestId, String topic) {
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
