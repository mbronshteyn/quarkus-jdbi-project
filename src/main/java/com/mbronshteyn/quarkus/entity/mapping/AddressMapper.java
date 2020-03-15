package com.mbronshteyn.quarkus.entity.mapping;

import com.mbronshteyn.quarkus.entity.Address;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressMapper implements RowMapper<Address> {
    @Override
    public Address map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Address(
                rs.getLong("ID"),
                rs.getString("COUNTRY"),
                rs.getString("CITY"),
                rs.getString("STREET"),
                rs.getString("POST_CODE")
        );
    }
}
