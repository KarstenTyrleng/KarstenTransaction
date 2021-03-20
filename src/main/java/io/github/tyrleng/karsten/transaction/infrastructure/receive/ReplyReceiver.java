package io.github.tyrleng.karsten.transaction.infrastructure.receive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.tyrleng.karsten.transaction.application.RequestServicerImpl;

import javax.inject.Inject;

public class ReplyReceiver {


    RequestServicerImpl requestServicer;


    @Inject
    public ReplyReceiver (RequestServicerImpl requestServicer) {
        this.requestServicer = requestServicer;
    }


    public void handleReply (String reply) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(reply);
            JsonNode requestTypeNode = rootNode.get("requestType");
            String requestType = requestTypeNode.asText();

            JsonNode requestIdNode = rootNode.get("requestId");
            int requestId = requestIdNode.asInt();

            switch (requestType) {
                case "getAccount": {
                    // interpret the Json according to what the command expects to be returned.
                    // find the request id then call RequestServicerImpl to
                    requestServicer.receiveGetAccountsIdReply(requestId,null);
                }
            }

            // for this method, it'll know what each Command wants. RequestServicerImpl will then know which domain object wanted which command's reply.
            // parse out the reply string. The reply string should include the request id.
            // the reply may just be a status code.
            // the reply can also be a data response.
            // push to RequestServiceImpl to handle. Push back with the request id
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
