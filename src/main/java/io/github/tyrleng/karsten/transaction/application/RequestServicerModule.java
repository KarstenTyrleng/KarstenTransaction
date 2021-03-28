package io.github.tyrleng.karsten.transaction.application;

import dagger.Binds;
import dagger.Module;
import io.github.tyrleng.karsten.transaction.domain.outgoing.RequestServicer;

import javax.inject.Singleton;

@Module
public interface RequestServicerModule {
    @Binds
    @Singleton  
    RequestServicer bindRequestServicer (RequestServicerImpl requestServicer);
}
