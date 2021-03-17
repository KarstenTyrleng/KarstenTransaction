package io.github.tyrleng.karsten.transaction.infrastructure.send;

import dagger.internal.Factory;
import javax.annotation.processing.Generated;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class EventPublishSender_Factory implements Factory<EventPublishSender> {
  @Override
  public EventPublishSender get() {
    return newInstance();
  }

  public static EventPublishSender_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static EventPublishSender newInstance() {
    return new EventPublishSender();
  }

  private static final class InstanceHolder {
    private static final EventPublishSender_Factory INSTANCE = new EventPublishSender_Factory();
  }
}
