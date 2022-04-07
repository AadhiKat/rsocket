package com.aadhikat.rsocket.service;

import com.aadhikat.rsocket.dto.RequestDto;
import com.aadhikat.rsocket.dto.ResponseDto;
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

    /**
     * Assume that we receive a number. Let's return the square of the number in response.
     *
     * @param payload
     * @return
     */
    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        return Mono.fromSupplier(() -> {
            RequestDto requestDto = ObjectUtil.toObject(payload, RequestDto.class);
            ResponseDto responseDto = new ResponseDto(requestDto.getInput() , (requestDto.getInput() * requestDto.getInput()));
            return ObjectUtil.toPayload(responseDto);
        });
    }
}
