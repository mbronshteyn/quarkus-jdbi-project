package com.mbronshteyn.quarkus.dao;

import com.mbronshteyn.quarkus.entity.QueueItem;
import com.mbronshteyn.quarkus.entity.mapping.TestQueueMapper;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindBeanList;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.sql.Time;
import java.util.List;

@RegisterRowMapper(TestQueueMapper.class)
public interface QueueItemDao {
    @SqlUpdate("INSERT into test_queue ( id, time_created, value ) " +
            "VALUES ( :id, :time_created, :value )")
    int add(@Bind("id") String id,
            @Bind("time_created") Time time,
            @Bind("value") String value);

    @SqlUpdate("INSERT into test_queue ( id, time_created, value ) " +
            "VALUES ( :id, :timeCreated, :value )")
    int add(@BindBean QueueItem item);

    @SqlBatch("INSERT into test_queue ( id, time_created, value ) " +
            "VALUES ( :item.id, :item.timeCreated, :item.value )")
    int[] addItems(@BindBean("item") Iterable<QueueItem> items);

    @SqlQuery("SELECT * FROM test_queue q ORDER BY q.time_created FOR UPDATE SKIP LOCKED LIMIT 1")
    QueueItem takeNext();

    @SqlQuery("SELECT * FROM test_queue q ORDER BY q.time_created")
    @RegisterBeanMapper(QueueItem.class)
    List<QueueItem> findAll();

    @SqlUpdate("DELETE FROM test_queue WHERE id = :id")
    int deleteById(@Bind("id") String id);

    @SqlQuery("SELECT count(1) FROM test_queue")
    int count();
}
