package io.github.tyrleng.karsten.transaction.infrastructure.receive;

import dagger.internal.Factory;
import io.github.tyrleng.karsten.transaction.application.TransactionService;
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
public final class EventSubscribeReceiver_Factory implements Factory<EventSubscribeReceiver> {
  private final Provider<TransactionService> transactionServiceProvider;

  public EventSubscribeReceiver_Factory(Provider<TransactionService> transactionServiceProvider) {
    this.transactionServiceProvider = transactionServiceProvider;
  }

  @Override
  public EventSubscribeReceiver get() {
    return newInstance(transactionServiceProvider.get());
  }

  public static EventSubscribeReceiver_Factory create(
      Provider<TransactionService> transactionServiceProvider) {
    return new EventSubscribeReceiver_Factory(transactionServiceProvider);
  }

  public static EventSubscribeReceiver newInstance(TransactionService transactionService) {
    return new EventSubscribeReceiver(transactionService);
  }
}
