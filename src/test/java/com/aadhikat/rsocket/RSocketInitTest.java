package com.aadhikat.rsocket;

import com.aadhikat.rsocket.dto.RequestDto;
import com.aadhikat.rsocket.dto.ResponseDto;
import com.aadhikat.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RSocketInitTest {
    private RSocket rSocket;
    @BeforeAll
    public void setup() {
        this.rSocket = RSocketConnector.create().connect(TcpClientTransport.create("localhost", 6565)).block();
    }
    @Test
    public void fireAndForget() {
        Mono<Void> mono = this.rSocket.fireAndForget(ObjectUtil.toPayload(new RequestDto(5)));
        StepVerifier.create(mono).verifyComplete();
    }

    @Test
    public void requestResponse() {
        Mono<ResponseDto> mono = this.rSocket.requestResponse(ObjectUtil.toPayload(new RequestDto(5))).map(p -> ObjectUtil.toObject(p, ResponseDto.class)).doOnNext(System.out::println);
        StepVerifier.create(mono).expectNextCount(1).verifyComplete();
    }

    @Test
    public void requestStream() {
        Flux<ResponseDto> flux =
                this.rSocket
                        .requestStream(ObjectUtil.toPayload(new RequestDto(5)))
                        .map(p -> ObjectUtil.toObject(p, ResponseDto.class))
                        .doOnNext(System.out::println)
                        .take(4);
        StepVerifier.create(flux).expectNextCount(4).verifyComplete();
    }
}
