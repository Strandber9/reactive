package com.example.reactive.study;

import java.time.Duration;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example8_3 {

    @SneakyThrows
    public static void main(String[] args) {
        Flux.interval(Duration.ofMillis(1L)).onBackpressureDrop(dropped -> log.info("# dropped: {}", dropped))
                .publishOn(Schedulers.parallel()).subscribe(data -> {
                    log.info("# onNext: {}", data);
                    try {
                        Thread.sleep(5L);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }, error -> log.error("# onError", error));

        Thread.sleep(2000L);
    }
}
