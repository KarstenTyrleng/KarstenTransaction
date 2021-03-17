package io.github.tyrleng.karsten.transaction.infrastructure.send;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.tyrleng.karsten.transaction.application.EventPublisher;
import io.github.tyrleng.karsten.transaction.domain.Transaction;
import io.github.tyrleng.karsten.transaction.domain.event.publish.BasePublishEvent;
import io.github.tyrleng.karsten.transaction.domain.event.publish.TransactionCreatedEvent;
import io.github.tyrleng.karsten.transaction.infrastructure.MessageLoop;
import io.github.tyrleng.karsten.transaction.infrastructure.domainConverter.TransactionConverter;

import javax.inject.Inject;

/**
 * All knowledge about events (which kind of events) etc are to be kept here.
 * The converters only know how to return a Json representation of a Domain Object.
 * Unfortunately, will only need to possess knowledge of Domain Objects too.
 * So this is a pretty connected class.
 */

public class EventPublishSender implements EventPublisher {

    public static class PubEnvelope {
        private String topic;
        private String contents;
        public PubEnvelope(String topic, String contents) {
            this.topic = topic;
            this.contents = contents;
        }

        public String getTopic() {return  topic;}
        public String getContents() {return contents;}
    }

    @Inject
    public EventPublishSender() {

    }

    @Override
    public void publish(BasePublishEvent publishEvent) {
        try {
            if (publishEvent instanceof TransactionCreatedEvent) {
                Transaction transaction = ((TransactionCreatedEvent) publishEvent).getTransaction();
                String transactionJson = new TransactionConverter(transaction).provideDomainObjectAsJson();
                PubEnvelope envelope = new PubEnvelope("Transactions.TransactionCreated", transactionJson);
                MessageLoop.pubStack.push(envelope);
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
