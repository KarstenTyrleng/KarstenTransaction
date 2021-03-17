package io.github.tyrleng.karsten.transaction.domain.outgoing;

public interface RequestServicer {
    // implementation should be in appliation package.
    
    void handleRequest(Request request, Object objectMakingRequest);
}
