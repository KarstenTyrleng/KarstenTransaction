package io.github.tyrleng.karsten.transaction;

import dagger.Component;
import io.github.tyrleng.karsten.transaction.infrastructure.InfraModule;
import io.github.tyrleng.karsten.transaction.infrastructure.MessageLoop;

import javax.inject.Singleton;

@Component (modules = InfraModule.class)
@Singleton
public interface MessageLoopFactory {
    MessageLoop createMessageLoop();
}
