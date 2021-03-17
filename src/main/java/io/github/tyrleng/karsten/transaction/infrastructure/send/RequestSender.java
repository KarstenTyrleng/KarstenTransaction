package io.github.tyrleng.karsten.transaction.infrastructure.send;

import io.github.tyrleng.karsten.transaction.application.Requester;
import io.github.tyrleng.karsten.transaction.domain.query.Request;

import javax.inject.Inject;

public class RequestSender implements Requester {

    @Override
    public void request(Request request) {

    }

    @Inject
    public RequestSender() {

    }
}
