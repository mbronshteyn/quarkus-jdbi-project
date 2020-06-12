package com.mbronshteyn.quarkus.service;

import com.google.common.flogger.FluentLogger;
import com.mbronshteyn.quarkus.bl.DatabaseConnector;
import com.mbronshteyn.quarkus.dao.QueueItemDao;
import com.mbronshteyn.quarkus.entity.QueueItem;
import com.mbronshteyn.quarkus.util.QueueItemValueGenerator;
import org.jdbi.v3.core.Jdbi;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Time;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class TestQueueService {
    @Inject
    DatabaseConnector databaseConnector;

    Scheduler scheduler;

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public List<QueueItem> list() {
        return databaseConnector.getJdbi().withExtension(QueueItemDao.class, QueueItemDao::findAll);
    }

    public int count() {
        return databaseConnector.getJdbi().withExtension(QueueItemDao.class, QueueItemDao::count);
    }

    public Integer add(final QueueItem queueItem) {
        Jdbi jdbi = databaseConnector.getJdbi();
        final QueueItem qi = queueItem;
        return jdbi.withExtension(QueueItemDao.class,
                dao -> dao.add(qi.getId(), new Time(qi.getTimeCreated().toEpochMilli()), qi.getValue()));
    }

    public int[] add(Iterable<QueueItem> queueItems) {
        int[] results = databaseConnector.getJdbi().withExtension(QueueItemDao.class,
                dao -> dao.addItems(queueItems));
        return results;
    }

    public void startProcessors(int count, int minDelay, int maxDelay) {
        if (scheduler != null) {
            throw new IllegalStateException("queue consumer scheduler already running");
        }
        scheduler = Schedulers.newBoundedElastic(count, Integer.MAX_VALUE, "test_queue_consumer");
        for (int i = 0; i < count; ++i) {
            String workerId = "worker-" + i;
            scheduler.schedulePeriodically(() -> processNextItem(workerId, minDelay, maxDelay),
                    0, 10, TimeUnit.MILLISECONDS);
        }
    }

    public void stopProcessors() {
        if (scheduler == null) {
            return;
        }
        scheduler.dispose();
        scheduler = null;
    }


    private void processNextItem(String workerId, int minDelay, int maxDelay) {
        Jdbi jdbi = databaseConnector.getJdbi();
        jdbi.inTransaction(h -> {
            QueueItem item = jdbi.withExtension(QueueItemDao.class, QueueItemDao::takeNext);
            if (item == null) {
                return h;
            }
            sleepRandom(minDelay, maxDelay);
            logger.atInfo().log("worker " + workerId + " value: " + item.getValue());
            jdbi.withExtension(QueueItemDao.class, qi -> {
                return qi.deleteById(item.getId());
            });
            return h;
        });
    }

    private static void sleepRandom(int minMillis, int maxMillis) {
        int delay = new Random().nextInt(maxMillis - minMillis) + minMillis;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }


}
