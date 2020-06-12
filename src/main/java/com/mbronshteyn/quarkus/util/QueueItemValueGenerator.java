package com.mbronshteyn.quarkus.util;

import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Singleton
public class QueueItemValueGenerator {

    protected AtomicInteger sequenceNumber = new AtomicInteger();
    public String next() {
        return "item " + sequenceNumber.incrementAndGet();
    }

    public List<String> next(int n) {
        return nextAsStream(n).collect(Collectors.toList());
    }

    public Stream<String> nextAsStream(int n) {
        return IntStream.range(0, n).mapToObj(i -> this.next());
    }
}
