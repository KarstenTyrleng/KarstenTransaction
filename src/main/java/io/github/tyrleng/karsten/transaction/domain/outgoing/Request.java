package io.github.tyrleng.karsten.transaction.domain.outgoing;

import lombok.Getter;

import java.util.Random;

public interface Request {
    boolean isCommand();
    String getTopic();
    String getContents();
}
