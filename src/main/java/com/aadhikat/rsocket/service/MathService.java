package com.aadhikat.rsocket.service;

import com.aadhikat.rsocket.dto.RequestDto;
import com.aadhikat.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Mono;

public class MathService implements RSocket {

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        System.out.println("Receiving : " + ObjectUtil.toObject(payload, RequestDto.class));
        return Mono.empty();
    }
}
