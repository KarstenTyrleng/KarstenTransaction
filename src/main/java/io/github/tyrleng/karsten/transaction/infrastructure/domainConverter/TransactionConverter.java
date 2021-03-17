package io.github.tyrleng.karsten.transaction.infrastructure.domainConverter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.tyrleng.finance.Money;
import io.github.tyrleng.karsten.transaction.domain.Transaction;
import io.github.tyrleng.karsten.transaction.domain.entity.Account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

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
        System.out.println(json);
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

            jsonGenerator.writeNumberField("id", transaction.getId());
            jsonGenerator.writeStringField("date_created", transaction.getDateCreated().toString());

            jsonGenerator.writeFieldName("account_amount_credited");
            jsonGenerator.writeStartArray();
            HashMap<Account, Money> accountMoneyCredited = transaction.getAccountMoneyCredited();
            Set<Account> accountCredited = accountMoneyCredited.keySet();
            for (Account account : accountCredited) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("account", account.toString());
                jsonGenerator.writeStringField("amount", accountMoneyCredited.get(account).getMoneyWithCurrencyCode());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();

            jsonGenerator.writeFieldName("account_amount_debited");
            jsonGenerator.writeStartArray();
            HashMap<Account, Money> accountMoneyDebited = transaction.getAccountMoneyDebited();
            Set<Account> accountDebited = accountMoneyDebited.keySet();
            for (Account account : accountDebited) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("account", account.toString());
                jsonGenerator.writeStringField("amount", accountMoneyDebited.get(account).getMoneyWithCurrencyCode());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
        }
    }
}
