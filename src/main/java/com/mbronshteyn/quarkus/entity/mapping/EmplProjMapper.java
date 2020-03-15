package com.mbronshteyn.quarkus.entity.mapping;

import com.mbronshteyn.quarkus.entity.EmplProj;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmplProjMapper implements RowMapper<EmplProj> {

    @Override
    public EmplProj map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new EmplProj(
                rs.getLong("EMPLOYEE_ID"),
                rs.getLong("PROJECT_ID")
        );
    }
}
