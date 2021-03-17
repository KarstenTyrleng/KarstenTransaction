package io.github.tyrleng.karsten.transaction.infrastructure.send;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.tyrleng.karsten.transaction.application.RequestPublisher;
import io.github.tyrleng.karsten.transaction.domain.outgoing.Request;
import io.github.tyrleng.karsten.transaction.infrastructure.MessageLoop;
import io.github.tyrleng.karsten.transaction.infrastructure.receive.ReplyReceiver;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class RequestSender implements RequestPublisher {

    ReplyReceiver replyReceiver;
    @Inject
    RequestSender (ReplyReceiver replyReceiver) {
        this.replyReceiver = replyReceiver;
    }

    private int requestId;

    @Override
    public void makeRequest(Request request, int requesterId) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(Request.class, new RequestSerializer());
            mapper.registerModule(module);
            requestId = requesterId;
            String requestString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
            MessageLoop.requestSendStack.push(requestString);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void handleReply(String replyString) {

    }

    private class RequestSerializer extends StdSerializer<Request> {

        RequestSerializer() {
            this(null);
        }

        protected RequestSerializer(Class<Request> t) {
            super(t);
        }

        @Override
        public void serialize(Request request, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            if (request.isCommand()) {
                jsonGenerator.writeStringField("requestType", "command");
            } else {
                jsonGenerator.writeStringField("requestType", "query");
            }
            jsonGenerator.writeStringField("topic", request.getTopic());

            jsonGenerator.writeNumberField("requestId", requestId);

            jsonGenerator.writeEndObject();
        }
    }
}
