package com.mbronshteyn.quarkus.entity.mapping;

import com.mbronshteyn.quarkus.entity.Fruit;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FruitMapper implements RowMapper<Fruit> {

    @Override
    public Fruit map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Fruit(
                rs.getString("uuid"),
                rs.getString("name"),
                rs.getString("description"));
    }
}
