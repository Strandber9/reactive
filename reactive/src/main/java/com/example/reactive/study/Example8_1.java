package com.example.reactive.study;

import java.util.concurrent.Flow.Subscription;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

@Slf4j
public class Example8_1 {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .doOnRequest(data -> log.info("# doOnRequest: {}", data))
                .subscribe(new BaseSubscriber<Integer>() {

                    @Override
                    protected void hookFinally(SignalType type) {
                        log.info("hookFinally-SignalType : {}", type);
                        super.hookFinally(type);
                    }

                    @Override
                    protected void hookOnCancel() {
                        log.info("hookOnCancel");
                        super.hookOnCancel();
                    }

                    @Override
                    protected void hookOnComplete() {
                        log.info("hookOnComplete");
                        super.hookOnComplete();
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        log.info("hookOnError: {}", throwable);
                        super.hookOnError(throwable);
                    }

                    @SneakyThrows
                    @Override
                    protected void hookOnNext(Integer value) {
                        Thread.sleep(2000L);
                        log.info("hookOnNext: {}", value);
                        request(1);
                    }

                    @Override
                    protected void hookOnSubscribe(org.reactivestreams.Subscription subscription) {
                        log.info("hookOnSubscribe: {}", subscription);
                        request(1);
                    }

                });
    }

}
