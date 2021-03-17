package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.domain.outgoing.Request;

public interface RequestPublisher {

    void makeRequest (Request request, int requesterId);

}
