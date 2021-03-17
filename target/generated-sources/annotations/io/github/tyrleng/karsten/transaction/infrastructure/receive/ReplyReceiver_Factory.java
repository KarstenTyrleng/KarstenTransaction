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
public final class ReplyReceiver_Factory implements Factory<ReplyReceiver> {
  private final Provider<TransactionService> transactionServiceProvider;

  public ReplyReceiver_Factory(Provider<TransactionService> transactionServiceProvider) {
    this.transactionServiceProvider = transactionServiceProvider;
  }

  @Override
  public ReplyReceiver get() {
    return newInstance(transactionServiceProvider.get());
  }

  public static ReplyReceiver_Factory create(
      Provider<TransactionService> transactionServiceProvider) {
    return new ReplyReceiver_Factory(transactionServiceProvider);
  }

  public static ReplyReceiver newInstance(TransactionService transactionService) {
    return new ReplyReceiver(transactionService);
  }
}
