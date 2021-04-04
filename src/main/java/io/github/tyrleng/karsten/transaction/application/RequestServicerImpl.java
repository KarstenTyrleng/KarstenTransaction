package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.domain.outgoing.Request;
import io.github.tyrleng.karsten.transaction.domain.outgoing.RequestServicer;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

public class RequestServicerImpl implements RequestServicer {

    RequestPublisher requestPublisher;


    @Inject
    public RequestServicerImpl (RequestPublisher requestPublisher) {
        this.requestPublisher = requestPublisher;
        accountToCallbackMap = new HashMap<>();
    }

    private int generateRequestId () {
        return new Random().nextInt(100);
    }

    /**
     * Getting Account Ids at the start of the program's lifespan.
     * Callback defined by the domain object receiving the reply, which in this case, is Transaction Service.
     */

    HashMap<Integer, Consumer<ArrayList<UUID>>> accountToCallbackMap;

    public void makeGetAccountIdRequest(Request request, Consumer<ArrayList<UUID>> callback) {
        int id = generateRequestId();
        accountToCallbackMap.put(id, callback);
        requestPublisher.makeRequest(request,id);
    }

    public void receiveGetAccountsIdReply (int requestId, ArrayList<UUID> accountIdList) {
        Consumer<ArrayList<UUID>> callback = accountToCallbackMap.get(requestId);
        callback.accept(accountIdList);
    }
}
