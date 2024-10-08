package com.product.server.koi_control_application.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;


@Service
public class SSEService<T>{

    private final Sinks.Many<T> sink;

    public SSEService() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public void emitEvent(T event) {
        sink.tryEmitNext(event);
    }

    public Flux<T> getEventStream() {
        return sink.asFlux();
    }
}