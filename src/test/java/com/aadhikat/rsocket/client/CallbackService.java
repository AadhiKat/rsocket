package com.aadhikat.rsocket.client;

import com.aadhikat.rsocket.dto.ResponseDto;
import com.aadhikat.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Mono;


public class CallbackService implements RSocket {
    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        System.out.println("Client received : " + ObjectUtil.toObject(payload, ResponseDto.class));
        return Mono.empty();
    }
}
