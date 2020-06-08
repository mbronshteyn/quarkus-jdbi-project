package com.mbronshteyn.expbackoff;

import com.google.common.flogger.FluentLogger;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

import static io.github.resilience4j.retry.IntervalFunction.ofExponentialBackoff;
import static io.github.resilience4j.retry.IntervalFunction.ofExponentialRandomBackoff;
import static java.util.Collections.nCopies;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BackoffWithJitterTest {

    static final Long INITIAL_INTERVAL = 1000L;
    static final Double MULTIPLIER = 2.0D;
    static final Double RANDOMIZATION_FACTOR = 0.6D;
    static final Integer MAX_RETRIES = 4;
    private static final int NUM_CONCURRENT_CLIENTS = 8;
    private final FluentLogger logger = FluentLogger.forEnclosingClass();
    private ChannelService service;

    @Before
    public void setUp() {
        service = mock(ChannelService.class);
    }

    @Test
    public void whenRetryExponentialBackoff_thenRetriedConfiguredNoOfTimes() {
        IntervalFunction intervalFn = ofExponentialBackoff(INITIAL_INTERVAL, MULTIPLIER);
        Function<String, String> pingPongFn = getRetryableChannelFn(intervalFn);

        when(service.call(anyString())).thenThrow(ChannelServiceException.class);
        try {
            pingPongFn.apply("Hello");
        } catch (ChannelServiceException e) {
            verify(service, times(MAX_RETRIES)).call(anyString());
        }
    }

    @Test
    public void whenRetryExponentialBackoffWithoutJitter_thenThunderingHerdProblemOccurs() throws InterruptedException {
        IntervalFunction intervalFn = ofExponentialBackoff(INITIAL_INTERVAL, MULTIPLIER);
        test(intervalFn);
    }

    @Test
    public void whenRetryExponentialBackoffWithJitter_thenRetriesAreSpread() throws InterruptedException {
        IntervalFunction intervalFn = ofExponentialRandomBackoff(INITIAL_INTERVAL, MULTIPLIER, RANDOMIZATION_FACTOR);
        test(intervalFn);
    }

    private void test(IntervalFunction intervalFn) throws InterruptedException {
        Function<String, String> pingPongFn = getRetryableChannelFn(intervalFn);
        ExecutorService executors = newFixedThreadPool(NUM_CONCURRENT_CLIENTS);
        List<Callable<String>> tasks = nCopies(NUM_CONCURRENT_CLIENTS, () -> pingPongFn.apply("Hello"));

        when(service.call(anyString())).thenThrow(ChannelServiceException.class);

        executors.invokeAll(tasks);
    }

    private Function<String, String> getRetryableChannelFn(IntervalFunction intervalFn) {
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(MAX_RETRIES)
                .intervalFunction(intervalFn)
                .retryExceptions(ChannelServiceException.class)
                .build();
        Retry retry = Retry.of("pingpong", retryConfig);
        return Retry.decorateFunction(retry, ping -> {
            logger.atInfo().log("Invoked at %s", LocalDateTime.now());
            return service.call(ping);
        });
    }

    interface ChannelService {

        String call(String ping) throws ChannelServiceException;
    }

    class ChannelServiceException extends RuntimeException {

        public ChannelServiceException(String reason) {
            super(reason);
        }
    }
}

