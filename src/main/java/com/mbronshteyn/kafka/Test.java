package com.mbronshteyn.kafka;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Test {
    public static void main(String[] args) {

            int[] array = new int[] { 1, 2, 3, 4, 5, 6,7, 8, 9, 10 } ;

             Flux.range(1, 10)
              .parallel(2)
                    .runOn(Schedulers.parallel())
                    .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
    }
}
