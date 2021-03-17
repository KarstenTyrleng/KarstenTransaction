package io.github.tyrleng.karsten.transaction.infrastructure;

import dagger.Binds;
import dagger.Module;
import io.github.tyrleng.karsten.transaction.application.EventPublisher;
import io.github.tyrleng.karsten.transaction.application.Repository;
import io.github.tyrleng.karsten.transaction.application.RequestPublisher;
import io.github.tyrleng.karsten.transaction.infrastructure.send.EventPublishSender;
import io.github.tyrleng.karsten.transaction.infrastructure.send.RequestSender;

import javax.inject.Singleton;

@Module
public interface InfraModule {

    @Binds
    @Singleton
    Repository bindRepository (RepositoryImpl repository);

    @Binds
    EventPublisher bindEventPublisher (EventPublishSender eventPublishSender);

    @Binds
    RequestPublisher bindRequestPublisher (RequestSender requestSender);
}
