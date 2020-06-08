package com.mbronshteyn.expbackoff;

import io.github.resilience4j.core.IntervalFunction;

import java.util.function.Function;

import static io.github.resilience4j.retry.IntervalFunction.ofExponentialBackoff;

public class ApplicationBackoff {

    public static void main(String[] args) {

        Long INITIAL_INTERVAL = 1000L;
        Double MULTIPLIER = 2.0D;

        ChannelService service = new ChannelService();
        BackoffWithJitter backoffWithJitter = new BackoffWithJitter();

        IntervalFunction intervalFn = ofExponentialBackoff(INITIAL_INTERVAL, MULTIPLIER);
        Function<String, String> channelFn = backoffWithJitter.getRetryableChannelFn(intervalFn, service);

        channelFn.apply("Hello World!!!!");

    }

}
