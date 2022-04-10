package com.aadhikat.rsocket.service;

import io.rsocket.Payload;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FreeService extends MathService {

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        return Mono.empty();  // Not allowed in Free service
    }

    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        return Mono.empty();  // Not Allowed in free service. We can also emit error signal here.
    }

    @Override
    public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
        return super.requestChannel(payloads).take(3); // Allowed to take only first 3 values
     }

    @Override
    public Flux<Payload> requestStream(Payload payload) {
        return super.requestStream(payload).take(3); // Only first 3 values in free service.
    }
}
