package io.github.tyrleng.karsten.transaction.infrastructure.domainConverter;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonProvider {
    String provideDomainObjectAsJson () throws JsonProcessingException;
}
