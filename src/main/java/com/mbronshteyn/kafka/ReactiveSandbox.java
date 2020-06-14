package com.mbronshteyn.kafka;

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
            .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));

    // sequental procssing on single thread
    //      String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
    //      Observable<String> observable = Observable.fromArray(letters);
    //      observable.subscribe(
    //              i ->  {
    //                System.out.println("Thread " + Thread.currentThread().getId());
    //                System.out.println( "Process and commit transaction" );
    //              },  //OnNext
    //              ( i ) -> System.out.println( "Rollback the transaction"), //OnError
    //              () ->  System.out.println( "Completed" ) //OnCompleted
    //      );


    System.out.println("Checkpoint #2 on " + Thread.currentThread().getName() + "\n\n");
    Observable<Integer> vals = Observable.range(1, 10);

    vals.flatMap(
            val ->
                    Observable.just(val)
                            .subscribeOn(io.reactivex.schedulers.Schedulers.computation())
                            .map(ReactiveSandbox::intenseCalculation))
            .subscribe();

    System.out.println("Checkpoint #3 on " + Thread.currentThread().getName() + "\n\n");
  }

  // process the value
  public static int intenseCalculation(int i) {
    try {
      System.out.println("Calculating " + i + " on " + Thread.currentThread().getName());
      Thread.sleep(randInt(1000, 5000));
      return i;
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static int randInt(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }
}
