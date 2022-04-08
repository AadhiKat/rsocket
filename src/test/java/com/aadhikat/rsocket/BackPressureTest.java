package com.aadhikat.rsocket;

import com.aadhikat.rsocket.client.CallbackService;
import com.aadhikat.rsocket.dto.RequestDto;
import com.aadhikat.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
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

public class BackPressureTest {

    private RSocket rSocket;
    @BeforeAll
    //Here we have to send the rsocket implementation which overrides the fireAndForget method since the client side needs a SocketAcceptor
    public void setup() {
        this.rSocket = RSocketConnector
                .create()
                .connect(TcpClientTransport.create("localhost", 6565)).block();
    }
    @Test
    public void backpressure() {
        Flux<String> flux = this.rSocket.requestStream(DefaultPayload.create(""))
                .map(Payload::getDataUtf8)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(System.out::println);

        StepVerifier.create(flux).expectNextCount(1000).verifyComplete();
    }
}
