package io.github.tyrleng.karsten.transaction.infrastructure;

import io.github.tyrleng.karsten.transaction.infrastructure.receive.EventSubscribeReceiver;
import io.github.tyrleng.karsten.transaction.infrastructure.receive.ReplyReceiver;
import io.github.tyrleng.karsten.transaction.infrastructure.receive.RequestReceiver;
import io.github.tyrleng.karsten.transaction.infrastructure.send.EventPublishSender;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

public class MessageLoop {

    public static ZMQ.Socket subSocket;
    ZMQ.Socket pubSocket;
    ZMQ.Socket replySocket;
    ZMQ.Socket requestSocket;
    ZContext context;

    @Inject EventSubscribeReceiver eventSubscribeReceiver;
    @Inject ReplyReceiver replyReceiver;
    @Inject RequestReceiver requestReceiver;

    public static Stack<EventPublishSender.PubEnvelope> pubStack;
    public static Stack<String> requestSendStack;

    @Inject
    public MessageLoop () {
        requestSendStack = new Stack<>();
        pubStack = new Stack<>();
    }

    public void enterMainLoop() {
        try {
            context = new ZContext();
            subSocket = context.createSocket(SocketType.SUB);
            pubSocket = context.createSocket(SocketType.PUB);
            replySocket = context.createSocket(SocketType.REP);
            requestSocket = context.createSocket(SocketType.REQ);


            ZMQ.Socket syncSocket = context.createSocket(SocketType.REP);
            int subscribers = 0;
            int expectedSubscribers = 1;

            // Transaction's Ports are 500x
            replySocket.bind("tcp://localhost:5000");
            pubSocket.bind("tcp://localhost:5001");
            syncSocket.bind("tcp://localhost:5002");

            // Physical's Ports are 501x
            requestSocket.connect("tcp://localhost:5010");
            subSocket.connect("tcp://localhost:5011");

            Stack<String> topicsList = eventSubscribeReceiver.topicsToSubscribeTo();
            while (!topicsList.isEmpty()) {
                subSocket.subscribe(topicsList.pop());
            }

            ZMQ.Poller poller = context.createPoller(2);

            poller.register(subSocket, ZMQ.Poller.POLLIN);
            poller.register(replySocket, ZMQ.Poller.POLLIN);

            while (!Thread.currentThread().isInterrupted()) {

                poller.poll(100);
                if (poller.pollin(0)) {
                    String topic = subSocket.recvStr();
                    String contents = subSocket.recvStr();
                    eventSubscribeReceiver.handleMessage(topic,contents);
                }
                if (poller.pollin(1)) {
                    String message = replySocket.recvStr();
                    String reply = requestReceiver.handleRequest(message);
                    replySocket.send(reply);
                }

                if (!requestSendStack.isEmpty()) {
                    requestSocket.send(requestSendStack.pop());
                    String reply = requestSocket.recvStr();
                    replyReceiver.handleReply(reply);
                }

                if (subscribers < expectedSubscribers) {
                    String foo = syncSocket.recvStr(ZMQ.DONTWAIT);
                    if (foo != null) {
                        System.out.println("received subscriber");
                        subscribers++;
                    }
                }

                if (!pubStack.isEmpty()) {
                    if (subscribers == expectedSubscribers) {
                        EventPublishSender.PubEnvelope envelope = pubStack.pop();
                        System.out.println("Publishing");
                        pubSocket.sendMore(envelope.getTopic());
                        pubSocket.send(envelope.getContents());
                    }
                }
//                if (!pubStack.isEmpty()) {
////                    poller.register(pubSocket, ZMQ.Poller.POLLOUT);
//                    EventPublishSender.PubEnvelope envelope = pubStack.pop();
//                    pubSocket.sendMore(envelope.getTopic());
//                    pubSocket.send(envelope.getContents());
//                }
//                if (!replySendStack.isEmpty()) {
////                    poller.register(reqSocket, ZMQ.Poller.POLLOUT);
//                    reply
//
//                }
//
//                // unsure of what number to put into here.
//                poller.poll(100);
//
//                if (poller.pollin(0)) {
//                    String topic = subSocket.recvStr();
//                    String contents = subSocket.recvStr();
//                    eventSubscribeReceiver.handleMessage(topic,contents);
//                }
//                if (poller.pollin(1)) {
//                    String message = replySocket.recvStr();
//                    // Check
//                    // If Reply, forward to ReplyReceiver
//                    // If Request, forward to RequestReceiver
//                }
//                if (poller.pollout(2)) {
//                    if (!pubStack.isEmpty()) {
//                        EventPublishSender.PubEnvelope envelope = pubStack.pop();
//                        pubSocket.sendMore(envelope.getTopic());
//                        pubSocket.send(envelope.getContents());
//                        poller.unregister(pubSocket);
//                    }
//                    else {
//                        SendEnvelope envelope = replySendStack.pop();
//                        reqSocket.send(envelope.getMessage());
//                        if (envelope instanceof RequestSender.RequestSendEnvelope) {
//                            RequestSender.RequestSendEnvelope reqEnvelope = (RequestSender.RequestSendEnvelope)envelope;
//                            ReplyReceiver.AwaitingReply awaitingReply = new ReplyReceiver.AwaitingReply(reqEnvelope.id);
//                            ReplyReceiver.awaitingReplyStack.add(awaitingReply);
//                        }
//                        poller.unregister(reqSocket);
//                    }
//                }
//                if (poller.pollout(3)) {
//                    if (!replySendStack.isEmpty()) {
//                        SendEnvelope envelope = replySendStack.pop();
//                        reqSocket.send(envelope.getMessage());
//                        if (envelope instanceof RequestSender.RequestSendEnvelope) {
//                            RequestSender.RequestSendEnvelope reqEnvelope = (RequestSender.RequestSendEnvelope)envelope;
//                            ReplyReceiver.AwaitingReply awaitingReply = new ReplyReceiver.AwaitingReply(reqEnvelope.id);
//                            ReplyReceiver.awaitingReplyStack.add(awaitingReply);
//                        }
//                        poller.unregister(reqSocket);
//                    }
//
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
