package io.github.tyrleng.karsten.transaction.infrastructure.send;

import dagger.Binds;
import dagger.Module;
import io.github.tyrleng.karsten.transaction.application.RequestPublisher;

@Module
public abstract class RequesterModule {
    @Binds
    public abstract RequestPublisher bindRequester (RequestSender requestSender);
}
