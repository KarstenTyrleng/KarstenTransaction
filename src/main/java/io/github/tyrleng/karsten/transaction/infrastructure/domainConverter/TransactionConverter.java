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

            jsonGenerator.writeStringField("id", transaction.getId().toString());
            jsonGenerator.writeStringField("dateCreated", transaction.getDateCreated().toString());

            jsonGenerator.writeFieldName("accountAmountCredited");
            jsonGenerator.writeStartArray();
            HashMap<UUID, BigMoney> accountMoneyCredited = transaction.getAccountMoneyCredited();
            Set<UUID> accountCredited = accountMoneyCredited.keySet();
            for (UUID accountId : accountCredited) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("accountId", accountId.toString());
                String accountAndAmount = accountMoneyCredited.get(accountId).toString();
                String[] split = accountAndAmount.split(" ");
                jsonGenerator.writeStringField("currency", split[0]);
                jsonGenerator.writeStringField("amount", split[1]);
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();

            jsonGenerator.writeFieldName("accountAmountDebited");
            jsonGenerator.writeStartArray();
            HashMap<UUID, BigMoney> accountMoneyDebited = transaction.getAccountMoneyDebited();
            Set<UUID> accountDebited = accountMoneyDebited.keySet();
            for (UUID account : accountDebited) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("account", account.toString());
                jsonGenerator.writeStringField("amount", accountMoneyDebited.get(account).toString());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
        }
    }
}
