package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.domain.outgoing.Request;

import java.util.function.BiConsumer;

public interface RequestPublisher {

    void makeRequest (Request request, int requestId);

}
