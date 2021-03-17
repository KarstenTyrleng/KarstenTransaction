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
public final class RequestReceiver_Factory implements Factory<RequestReceiver> {
  private final Provider<TransactionService> transactionServiceProvider;

  public RequestReceiver_Factory(Provider<TransactionService> transactionServiceProvider) {
    this.transactionServiceProvider = transactionServiceProvider;
  }

  @Override
  public RequestReceiver get() {
    return newInstance(transactionServiceProvider.get());
  }

  public static RequestReceiver_Factory create(
      Provider<TransactionService> transactionServiceProvider) {
    return new RequestReceiver_Factory(transactionServiceProvider);
  }

  public static RequestReceiver newInstance(TransactionService transactionService) {
    return new RequestReceiver(transactionService);
  }
}
