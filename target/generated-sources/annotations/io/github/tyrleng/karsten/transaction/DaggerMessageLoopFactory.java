package io.github.tyrleng.karsten.transaction;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.internal.DoubleCheck;
import io.github.tyrleng.karsten.transaction.application.Repository;
import io.github.tyrleng.karsten.transaction.application.TransactionService;
import io.github.tyrleng.karsten.transaction.application.TransactionService_Factory;
import io.github.tyrleng.karsten.transaction.infrastructure.MessageLoop;
import io.github.tyrleng.karsten.transaction.infrastructure.MessageLoop_Factory;
import io.github.tyrleng.karsten.transaction.infrastructure.MessageLoop_MembersInjector;
import io.github.tyrleng.karsten.transaction.infrastructure.RepositoryImpl_Factory;
import io.github.tyrleng.karsten.transaction.infrastructure.receive.EventSubscribeReceiver;
import io.github.tyrleng.karsten.transaction.infrastructure.receive.ReplyReceiver;
import io.github.tyrleng.karsten.transaction.infrastructure.receive.RequestReceiver;
import io.github.tyrleng.karsten.transaction.infrastructure.receive.RequestReceiver_Factory;
import io.github.tyrleng.karsten.transaction.infrastructure.send.EventPublishSender_Factory;
import io.github.tyrleng.karsten.transaction.infrastructure.send.RequestSender_Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerMessageLoopFactory implements MessageLoopFactory {
  private Provider<Repository> bindRepositoryProvider;

  private Provider<TransactionService> transactionServiceProvider;

  private DaggerMessageLoopFactory() {

    initialize();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static MessageLoopFactory create() {
    return new Builder().build();
  }

  private EventSubscribeReceiver eventSubscribeReceiver() {
    return new EventSubscribeReceiver(transactionServiceProvider.get());
  }

  private ReplyReceiver replyReceiver() {
    return new ReplyReceiver(transactionServiceProvider.get());
  }

  private RequestReceiver requestReceiver() {
    return RequestReceiver_Factory.newInstance(transactionServiceProvider.get());
  }

  @SuppressWarnings("unchecked")
  private void initialize() {
    this.bindRepositoryProvider = DoubleCheck.provider((Provider) RepositoryImpl_Factory.create());
    this.transactionServiceProvider = DoubleCheck.provider(TransactionService_Factory.create(bindRepositoryProvider, (Provider) EventPublishSender_Factory.create(), (Provider) RequestSender_Factory.create()));
  }

  @Override
  public MessageLoop createMessageLoop() {
    return injectMessageLoop(MessageLoop_Factory.newInstance());
  }

  @CanIgnoreReturnValue
  private MessageLoop injectMessageLoop(MessageLoop instance) {
    MessageLoop_MembersInjector.injectEventSubscribeReceiver(instance, eventSubscribeReceiver());
    MessageLoop_MembersInjector.injectReplyReceiver(instance, replyReceiver());
    MessageLoop_MembersInjector.injectRequestReceiver(instance, requestReceiver());
    return instance;
  }

  public static final class Builder {
    private Builder() {
    }

    public MessageLoopFactory build() {
      return new DaggerMessageLoopFactory();
    }
  }
}
