package io.github.tyrleng.karsten.transaction.domain.outgoing;

import io.github.tyrleng.karsten.transaction.domain.Transaction;
import io.github.tyrleng.karsten.transaction.domain.entity.Account;

import java.util.ArrayList;
import java.util.function.BiConsumer;


/**
 * To be injected into domain objects.
 * Has to be specifically tailored for each command.
 * Make as many hashmaps as you need.
 */

public interface RequestServicer {
    // void helpMakeRequest(Request request, Object DomainObject, BiConsumer<DomainObject,ObjectToBeInserted> callback>);
}
