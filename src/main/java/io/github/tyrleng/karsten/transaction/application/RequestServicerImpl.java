package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.domain.entity.Account;
import io.github.tyrleng.karsten.transaction.domain.outgoing.Request;
import io.github.tyrleng.karsten.transaction.domain.outgoing.RequestServicer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Consumer;

@Singleton
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

//    ---

    HashMap<Integer, Consumer<ArrayList<Integer>>> accountToCallbackMap;

    public void makeGetAccountIdRequest(Request request, Consumer<ArrayList<Integer>> callback) {
        int id = generateRequestId();
        accountToCallbackMap.put(id, callback);
        requestPublisher.makeRequest(request,id);
    }

    public void receiveGetAccountsIdReply (int requestId, ArrayList<Integer> accountIdList) {
        Consumer<ArrayList<Integer>> callback = accountToCallbackMap.get(requestId);
        callback.accept(accountIdList);
    }
}
