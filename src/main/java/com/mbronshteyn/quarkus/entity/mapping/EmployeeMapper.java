package com.mbronshteyn.quarkus.entity.mapping;

import com.mbronshteyn.quarkus.entity.Employee;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {

    @Override
    public Employee map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Employee(
                rs.getLong("ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getDate("BIRTHDAY"),
                rs.getLong("ADDRESS_ID")
        );
    }
}
