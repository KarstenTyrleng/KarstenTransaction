package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.domain.outgoing.Request;
import io.github.tyrleng.karsten.transaction.domain.outgoing.RequestServicer;

import javax.inject.Inject;
import java.util.Random;

public class RequestServicerImpl implements RequestServicer {

    RequestPublisher requestPublisher;

    @Inject
    public RequestServicerImpl (RequestPublisher requestPublisher) {
        this.requestPublisher = requestPublisher;
    }

    @Override
    public void handleRequest(Request request, Object objectMakingRequest) {
        // RequestServicer has in-depth knowledge of what each domain object wants when the object makes a request of it.
        // call RequestPublisher
    }

//    private void generateRequestId () {
//        requestId = new Random().nextInt(100);
//    }

    public void handleRequestReply () {
        // when called on by the ReplyReceiver, will take the reply and send the reply to the Domain Objects.
        // knows which domain object to send based on requestId:domainObject id mapping.
    }
}
