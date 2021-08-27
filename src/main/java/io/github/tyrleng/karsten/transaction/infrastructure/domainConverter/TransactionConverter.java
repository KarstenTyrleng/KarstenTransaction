package io.github.tyrleng.karsten.transaction.infrastructure.domainConverter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.tyrleng.karsten.transaction.domain.Transaction;
import org.joda.money.BigMoney;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Provides Custom Serializer
 * Provides Json Data (will use the Object Mapper)
 * Provides Pub Envelope
 */
public class TransactionConverter implements JsonProvider {

    Transaction transaction;

    public TransactionConverter(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String provideDomainObjectAsJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Transaction.class, new TransactionSerializer());
        mapper.registerModule(module);
        String json =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction);
//        System.out.println(json);
        return json;
    }

    private class TransactionSerializer extends StdSerializer<Transaction> {

        TransactionSerializer () {
            this(null);
        }

        protected TransactionSerializer(Class<Transaction> t) {
            super(t);
        }

        @Override
        public void serialize(Transaction transaction, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//            jsonGenerator = jsonGenerator.useDefaultPrettyPrinter();
            jsonGenerator.writeStartObject();

            jsonGenerator.writeStringField("txnId", transaction.getId().toString());
            jsonGenerator.writeStringField("txnDateCreated", transaction.getDateCreated().toString());

            jsonGenerator.writeFieldName("txnCreditSplits");
            jsonGenerator.writeStartArray();
            HashMap<UUID, BigMoney> txnCreditSplits = transaction.getCreditSplits();
            Set<UUID> accountCreditedId = txnCreditSplits.keySet();
            for (UUID accountId : accountCreditedId) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("txnSplitAccountId", accountId.toString());
                String currencyAndAmount = txnCreditSplits.get(accountId).toString();
                String[] split = currencyAndAmount.split(" ");
                jsonGenerator.writeStringField("txnSplitCurrency", split[0]);
                jsonGenerator.writeStringField("txnSplitAmount", split[1]);
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();

            jsonGenerator.writeFieldName("txnDebitSplits");
            jsonGenerator.writeStartArray();
            HashMap<UUID, BigMoney> txnDebitSplits = transaction.getDebitSplits();
            Set<UUID> accountDebitedId = txnDebitSplits.keySet();
            for (UUID accountId : accountDebitedId) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("txnSplitAccountId", accountId.toString());
                String accountAndAmount = txnDebitSplits.get(accountId).toString();
                String[] split = accountAndAmount.split(" ");
                jsonGenerator.writeStringField("txnSplitCurrency", split[0]);
                jsonGenerator.writeStringField("txnSplitAmount", split[1]);
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
            jsonGenerator.close();
        }
    }
}
