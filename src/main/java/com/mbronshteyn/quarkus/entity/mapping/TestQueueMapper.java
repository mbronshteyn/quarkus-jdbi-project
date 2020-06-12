package com.mbronshteyn.quarkus.entity.mapping;

import com.mbronshteyn.quarkus.entity.QueueItem;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class TestQueueMapper implements RowMapper<QueueItem> {
    @Override
    public QueueItem map(ResultSet rs, StatementContext ctx) throws SQLException {
        return QueueItem.builder()
                .id(rs.getString("id"))
                .timeCreated(Instant.ofEpochMilli(rs.getTime("time_created").getTime()))
                .value(rs.getString("value"))
                .build();
    }
}
