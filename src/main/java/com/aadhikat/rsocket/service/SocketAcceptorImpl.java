package com.aadhikat.rsocket.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;

public class SocketAcceptorImpl implements SocketAcceptor {
    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload connectionSetupPayload, RSocket rSocket) {
        System.out.println("SocketAcceptorImpl - accept method");
//        return Mono.fromCallable(MathService::new);
//        return Mono.fromCallable(() -> new BatchJobService(rSocket));
        return Mono.fromCallable(FastProducerService::new);
    }
}
