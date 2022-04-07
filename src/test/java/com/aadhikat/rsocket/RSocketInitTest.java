package com.aadhikat.rsocket;

import com.aadhikat.rsocket.dto.RequestDto;
import com.aadhikat.rsocket.util.ObjectUtil;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
}
