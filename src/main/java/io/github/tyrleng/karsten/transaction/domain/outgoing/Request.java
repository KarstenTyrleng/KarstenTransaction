package io.github.tyrleng.karsten.transaction.domain.outgoing;

public interface Request {
    boolean isCommand();
    String getTopic();
    String getContents();
}
