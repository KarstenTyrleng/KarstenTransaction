package io.github.tyrleng.karsten.transaction.application;

import dagger.internal.Factory;
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
public final class TransactionService_Factory implements Factory<TransactionService> {
  private final Provider<Repository> repositoryProvider;

  private final Provider<EventPublisher> eventPublisherProvider;

  private final Provider<Requester> requesterProvider;

  public TransactionService_Factory(Provider<Repository> repositoryProvider,
      Provider<EventPublisher> eventPublisherProvider, Provider<Requester> requesterProvider) {
    this.repositoryProvider = repositoryProvider;
    this.eventPublisherProvider = eventPublisherProvider;
    this.requesterProvider = requesterProvider;
  }

  @Override
  public TransactionService get() {
    return newInstance(repositoryProvider.get(), eventPublisherProvider.get(), requesterProvider.get());
  }

  public static TransactionService_Factory create(Provider<Repository> repositoryProvider,
      Provider<EventPublisher> eventPublisherProvider, Provider<Requester> requesterProvider) {
    return new TransactionService_Factory(repositoryProvider, eventPublisherProvider, requesterProvider);
  }

  public static TransactionService newInstance(Repository repository, EventPublisher eventPublisher,
      Requester requester) {
    return new TransactionService(repository, eventPublisher, requester);
  }
}
