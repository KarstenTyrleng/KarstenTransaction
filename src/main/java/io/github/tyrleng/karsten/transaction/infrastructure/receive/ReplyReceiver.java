package io.github.tyrleng.karsten.transaction.infrastructure.receive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.tyrleng.karsten.transaction.application.RequestServicerImpl;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.UUID;

public class ReplyReceiver {


    RequestServicerImpl requestServicer;


    @Inject
    public ReplyReceiver (RequestServicerImpl requestServicer) {
        this.requestServicer = requestServicer;
    }



    // for this method, it'll know what each Command wants. RequestServicerImpl will then know which domain object wanted which command's reply.
    // parse out the reply string. The reply string should include the request id.
    // the reply may just be a status code.
    // the reply can also be a data response.
    // push to RequestServiceImpl to handle. Push back with the request id.

    public void handleReply (String reply) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(reply);

            JsonNode requestTypeNode = rootNode.get("requestType");
            String requestType = requestTypeNode.asText();

            switch (requestType) {
                case "query": {
                    handleQueries(rootNode);
                }
                case "command": {
                    handleCommands(rootNode);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void handleCommands(JsonNode rootNode) {

    }

    private void handleQueries(JsonNode rootNode) {

        JsonNode requestIdNode = rootNode.get("requestId");
        int requestId = requestIdNode.asInt();
        JsonNode topicNode = rootNode.get("topic");
        String topic = topicNode.asText();

        switch (topic) {
            case "getAccount": {
                ArrayList<UUID> accountIdList = new ArrayList<>();
                ArrayNode accountIdListNode = (ArrayNode)rootNode.get("accountIdList");
                for (JsonNode accountIdNode : accountIdListNode ) {
                    accountIdList.add(UUID.fromString(accountIdNode.asText()));
                }
                requestServicer.receiveGetAccountsIdReply(requestId,accountIdList);
            }
        }
    }
}
