package com.mbronshteyn.kafka;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Test {
    public static void main(String[] args) {
            Flux.range(1, 10)
                    .parallel(2)
                    .runOn(Schedulers.parallel())
                    .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
    }
}
