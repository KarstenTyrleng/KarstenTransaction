package io.github.tyrleng.karsten.transaction.infrastructure;

import dagger.MembersInjector;
import dagger.internal.InjectedFieldSignature;
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
public final class MessageLoop_MembersInjector implements MembersInjector<MessageLoop> {
  private final Provider<EventSubscribeReceiver> eventSubscribeReceiverProvider;

  private final Provider<ReplyReceiver> replyReceiverProvider;

  private final Provider<RequestReceiver> requestReceiverProvider;

  public MessageLoop_MembersInjector(
      Provider<EventSubscribeReceiver> eventSubscribeReceiverProvider,
      Provider<ReplyReceiver> replyReceiverProvider,
      Provider<RequestReceiver> requestReceiverProvider) {
    this.eventSubscribeReceiverProvider = eventSubscribeReceiverProvider;
    this.replyReceiverProvider = replyReceiverProvider;
    this.requestReceiverProvider = requestReceiverProvider;
  }

  public static MembersInjector<MessageLoop> create(
      Provider<EventSubscribeReceiver> eventSubscribeReceiverProvider,
      Provider<ReplyReceiver> replyReceiverProvider,
      Provider<RequestReceiver> requestReceiverProvider) {
    return new MessageLoop_MembersInjector(eventSubscribeReceiverProvider, replyReceiverProvider, requestReceiverProvider);
  }

  @Override
  public void injectMembers(MessageLoop instance) {
    injectEventSubscribeReceiver(instance, eventSubscribeReceiverProvider.get());
    injectReplyReceiver(instance, replyReceiverProvider.get());
    injectRequestReceiver(instance, requestReceiverProvider.get());
  }

  @InjectedFieldSignature("io.github.tyrleng.karsten.transaction.infrastructure.MessageLoop.eventSubscribeReceiver")
  public static void injectEventSubscribeReceiver(MessageLoop instance,
      EventSubscribeReceiver eventSubscribeReceiver) {
    instance.eventSubscribeReceiver = eventSubscribeReceiver;
  }

  @InjectedFieldSignature("io.github.tyrleng.karsten.transaction.infrastructure.MessageLoop.replyReceiver")
  public static void injectReplyReceiver(MessageLoop instance, ReplyReceiver replyReceiver) {
    instance.replyReceiver = replyReceiver;
  }

  @InjectedFieldSignature("io.github.tyrleng.karsten.transaction.infrastructure.MessageLoop.requestReceiver")
  public static void injectRequestReceiver(MessageLoop instance, RequestReceiver requestReceiver) {
    instance.requestReceiver = requestReceiver;
  }
}
