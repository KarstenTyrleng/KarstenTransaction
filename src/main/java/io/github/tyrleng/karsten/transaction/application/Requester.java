package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.domain.query.Request;

public interface Requester {

    void request(Request request);

}
