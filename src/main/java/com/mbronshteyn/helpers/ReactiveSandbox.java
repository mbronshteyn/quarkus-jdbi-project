package com.mbronshteyn.helpers;

import io.reactivex.Observable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ThreadLocalRandom;

public class ReactiveSandbox {
  public static void main(String[] args) throws Exception {

    System.out.println("Checkpoint #1 on " + Thread.currentThread().getName() + "\n\n");
    Flux.range(1, 10)
            .parallel(8)
            .runOn(Schedulers.parallel())
            .subscribe(i -> intenseCalculation(i));

    System.out.println("Checkpoint #2 on " + Thread.currentThread().getName() + "\n\n");

    Observable.just(1)
            .flatMap(
                    e ->
                            Observable.just(e)
                                    .subscribeOn(io.reactivex.schedulers.Schedulers.computation())
                                    .map(ReactiveSandbox::intenseCalculation))
            .subscribe();

    System.out.println("Checkpoint #3 on " + Thread.currentThread().getName() + "\n\n");

    Thread.sleep(10000);
  }

  // process the value
  public static int intenseCalculation(int i) {
    try {
      System.out.println("Calculating " + i + " on " + Thread.currentThread().getName());
      Thread.sleep(1000);
      return i;
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static int randInt(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }
}
