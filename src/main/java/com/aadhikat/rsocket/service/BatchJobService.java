package com.aadhikat.rsocket.service;

import com.aadhikat.rsocket.dto.RequestDto;
import com.aadhikat.rsocket.dto.ResponseDto;
import com.aadhikat.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Mono;

import java.time.Duration;


// The idea is, since we have the client side RSocket and Server side RSocket,
// the server side RSocket has a fireAndForget method, which finds the cube of a number.
// Since it is a very time-consuming process, we'll be returning a Mono.empty() and the computation will be done Asynchronously and
// the result will be returned using the client's fire and forget method after computing.

public class BatchJobService implements RSocket {

    private RSocket rSocket; // Client RSocket

    public BatchJobService(RSocket rSocket) {
        this.rSocket = rSocket;
    }

    @Override
    public Mono<Void> fireAndForget(Payload payload) {

        RequestDto requestDto = ObjectUtil.toObject(payload, RequestDto.class);
        System.out.println("Received" + requestDto);

        Mono.just(requestDto)
                .delayElement(Duration.ofSeconds(10))
                .doOnNext(i -> System.out.println("Emitting"))
                .flatMap(this::findCube)
                .subscribe();

        return Mono.empty();
    }

    private Mono<Void> findCube(RequestDto requestDto) {
        ResponseDto responseDto = new ResponseDto(requestDto.getInput(), requestDto.getInput() * requestDto.getInput() * requestDto.getInput());
        Payload payload = ObjectUtil.toPayload(responseDto);
        return this.rSocket.fireAndForget(payload);
    }
}

