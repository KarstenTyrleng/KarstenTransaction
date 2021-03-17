package io.github.tyrleng.karsten.transaction.infrastructure;

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
public final class RepositoryImpl_Factory implements Factory<RepositoryImpl> {
  @Override
  public RepositoryImpl get() {
    return newInstance();
  }

  public static RepositoryImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RepositoryImpl newInstance() {
    return new RepositoryImpl();
  }

  private static final class InstanceHolder {
    private static final RepositoryImpl_Factory INSTANCE = new RepositoryImpl_Factory();
  }
}
