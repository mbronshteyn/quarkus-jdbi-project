package com.mbronshteyn.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Properties;

public class MyProducer {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        Flux.range(0, 100)
                .parallel()
                .runOn(Schedulers.parallel())
                .subscribe(i -> {
                            System.out.println("Thread " + Thread.currentThread().getId());
                            producer.send(
                                    new ProducerRecord<String, String>(
                                            "my-topic", Integer.toString(i), Integer.toString(i)));
                        }, (error) -> {
                        },
                        () -> {
                            System.out.println("Finished array");
                            producer.close();
                        });

//        Mono.just("message from mono")
//                .publishOn(Schedulers.single())
//                .subscribe(message -> {
//                    System.out.println("Sending " + message);
//                    System.out.println("Thread " + Thread.currentThread().getId() );
//                    producer.send(
//                            new ProducerRecord<String, String>(
//                                    "my-topic", "xyz", message));
//                }, error -> {
//                }, () -> {
//                    System.out.println("Closing producer");
//                    producer.close();
//                });

        System.out.println("Before or after");

        Thread.sleep(10000);
    }


}
