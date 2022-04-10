package com.aadhikat.rsocket;

import com.aadhikat.rsocket.dto.RequestDto;
import com.aadhikat.rsocket.dto.ResponseDto;
import com.aadhikat.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConnectionSetupTest {

    private RSocketClient rSocketClient;
    @BeforeAll
    //Here we have to use the setupPayload method, to send it.
    public void setup() {
        Mono<RSocket> socketMono = RSocketConnector
                .create()
                .setupPayload(DefaultPayload.create("user:password"))
                .connect(TcpClientTransport.create("localhost", 6565))
                .doOnNext(r -> System.out.println("Going to connect"));

        rSocketClient = RSocketClient.from(socketMono);
    }
    @Test
    public void connectionSetupTest() throws InterruptedException {
        Payload payload = ObjectUtil.toPayload(new RequestDto(5));
        Flux<ResponseDto> flux = this.rSocketClient.requestStream(Mono.just(payload))
                .map(p -> ObjectUtil.toObject(p , ResponseDto.class))
                .doOnNext(System.out::println);
        StepVerifier.create(flux).expectNextCount(10).verifyComplete();
    }
}
