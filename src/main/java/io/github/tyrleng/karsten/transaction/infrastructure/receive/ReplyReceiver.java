package io.github.tyrleng.karsten.transaction.infrastructure.receive;

import io.github.tyrleng.karsten.transaction.application.TransactionService;

import javax.inject.Inject;
import java.util.List;

public class ReplyReceiver {

    public static List<AwaitingReply> awaitingReplyStack;

    public static class AwaitingReply {
        public AwaitingReply(int sentMessageId) {

        }
    }

    // check whether replies match any earlier requests made
    // format the replies from Json into something. A callback? A Json class?

    TransactionService transactionService;

    @Inject
    public ReplyReceiver (TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void handleReply (String reply) {

    }
}
