package com.mbronshteyn.quarkus.entity.mapping;

import com.mbronshteyn.quarkus.entity.Project;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMapper implements RowMapper<Project> {

    @Override
    public Project map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Project(rs.getLong("ID"),
                rs.getString("TITLE "));
    }
}
