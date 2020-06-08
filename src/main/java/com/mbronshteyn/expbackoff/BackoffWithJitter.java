package com.mbronshteyn.expbackoff;

import com.google.common.flogger.FluentLogger;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.function.Function;

@ApplicationScoped
public class BackoffWithJitter {

    static final Long INITIAL_INTERVAL = 1000L;
    static final Double MULTIPLIER = 2.0D;
    static final Double RANDOMIZATION_FACTOR = 0.6D;
    static final Integer MAX_RETRIES = 4;
    private static final int NUM_CONCURRENT_CLIENTS = 8;
    private final FluentLogger logger = FluentLogger.forEnclosingClass();

    @Inject
    private ChannelService service;

    public Function<String, String> getRetryableChannelFn(IntervalFunction intervalFn) {
        return getFunction(intervalFn, service);
    }

    private Function<String, String> getFunction(IntervalFunction intervalFn, ChannelService service) {
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(MAX_RETRIES)
                .intervalFunction(intervalFn)
                .retryExceptions(ChannelServiceException.class)
                .build();
        Retry retry = Retry.of("message", retryConfig);
        return Retry.decorateFunction(retry, msg -> {
            logger.atInfo().log("Invoked at %s", LocalDateTime.now());
            return service.sendOnChannel(msg);
        });
    }

    public Function<String, String> getRetryableChannelFn(IntervalFunction intervalFn, ChannelService service) {
        return getFunction(intervalFn, service);
    }
}

