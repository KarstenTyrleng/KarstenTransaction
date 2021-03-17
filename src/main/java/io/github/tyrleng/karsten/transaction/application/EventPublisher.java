package io.github.tyrleng.karsten.transaction.application;

import io.github.tyrleng.karsten.transaction.domain.event.publish.BasePublishEvent;

public interface EventPublisher {
    void publish (BasePublishEvent publishEvent);
}
