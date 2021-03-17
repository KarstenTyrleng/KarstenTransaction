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
public final class RequestSender_Factory implements Factory<RequestSender> {
  @Override
  public RequestSender get() {
    return newInstance();
  }

  public static RequestSender_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RequestSender newInstance() {
    return new RequestSender();
  }

  private static final class InstanceHolder {
    private static final RequestSender_Factory INSTANCE = new RequestSender_Factory();
  }
}
