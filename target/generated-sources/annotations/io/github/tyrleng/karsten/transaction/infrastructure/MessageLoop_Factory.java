package io.github.tyrleng.karsten.transaction.infrastructure;

import dagger.internal.Factory;
import io.github.tyrleng.karsten.transaction.infrastructure.receive.EventSubscribeReceiver;
import io.github.tyrleng.karsten.transaction.infrastructure.receive.ReplyReceiver;
import io.github.tyrleng.karsten.transaction.infrastructure.receive.RequestReceiver;
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
public final class MessageLoop_Factory implements Factory<MessageLoop> {
  private final Provider<EventSubscribeReceiver> eventSubscribeReceiverProvider;

  private final Provider<ReplyReceiver> replyReceiverProvider;

  private final Provider<RequestReceiver> requestReceiverProvider;

  public MessageLoop_Factory(Provider<EventSubscribeReceiver> eventSubscribeReceiverProvider,
      Provider<ReplyReceiver> replyReceiverProvider,
      Provider<RequestReceiver> requestReceiverProvider) {
    this.eventSubscribeReceiverProvider = eventSubscribeReceiverProvider;
    this.replyReceiverProvider = replyReceiverProvider;
    this.requestReceiverProvider = requestReceiverProvider;
  }

  @Override
  public MessageLoop get() {
    MessageLoop instance = newInstance();
    MessageLoop_MembersInjector.injectEventSubscribeReceiver(instance, eventSubscribeReceiverProvider.get());
    MessageLoop_MembersInjector.injectReplyReceiver(instance, replyReceiverProvider.get());
    MessageLoop_MembersInjector.injectRequestReceiver(instance, requestReceiverProvider.get());
    return instance;
  }

  public static MessageLoop_Factory create(
      Provider<EventSubscribeReceiver> eventSubscribeReceiverProvider,
      Provider<ReplyReceiver> replyReceiverProvider,
      Provider<RequestReceiver> requestReceiverProvider) {
    return new MessageLoop_Factory(eventSubscribeReceiverProvider, replyReceiverProvider, requestReceiverProvider);
  }

  public static MessageLoop newInstance() {
    return new MessageLoop();
  }
}
