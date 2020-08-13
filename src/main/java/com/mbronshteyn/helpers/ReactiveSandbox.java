package com.mbronshteyn.helpers;

import io.reactivex.Flowable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ReactiveSandbox {
  public static void main(String[] args) throws Exception {

    List<String> list = Arrays.asList( "1", "2", "3", "4", "5", "6", "7", "8"  );

    System.out.println("Checkpoint #1 on " + Thread.currentThread().getName() + "\n\n");
//    Flux.range(1, 10)
//            .parallel(8)
//            .runOn(Schedulers.parallel())
//            .subscribe(i -> intenseCalculation(i));

    System.out.println("Checkpoint #2 on " + Thread.currentThread().getName() + "\n\n");

//    Observable.fromIterable( list )
//            .flatMap(
//                    e ->
//                            Observable.just(e)
//                                    .subscribeOn(io.reactivex.schedulers.Schedulers.from( Executors.newScheduledThreadPool(8) ))
//                                    .map(ReactiveSandbox::intenseCalculation))
//            .subscribe();

//    Flowable.just(getRecord())
    Flowable.fromIterable(  list )
            .subscribeOn(io.reactivex.schedulers.Schedulers.from( Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() )))
            .subscribe(ReactiveSandbox::intenseCalculation);

    System.out.println("Checkpoint #3 on " + Thread.currentThread().getName() + "\n\n");


    Thread.sleep(10000);
  }
  public static int getRecord() {
    System.out.println("Insidie getRecord: thread name: " + Thread.currentThread().getName());
    return 18;
  }

  // process the value
  public static String intenseCalculation( String i ) {
      System.out.println("Calculating " + i + " on " + Thread.currentThread().getName());
//      Thread.sleep(1000);
      return i;
  }

  public static int randInt(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }
}
