package com.mbronshteyn.quarkus.dao;

import com.mbronshteyn.quarkus.entity.Address;
import com.mbronshteyn.quarkus.entity.mapping.AddressMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(AddressMapper.class)
public interface AddressDao {

    @SqlUpdate("CREATE TABLE \"ADDRESS\" ( \"ID\" bigint NOT NULL, \"COUNTRY\" character varying(255) NOT NULL," +
            " \"CITY\" character varying(255) NOT NULL, \"STREET\" character varying(255) NOT NULL," +
            " \"POST_CODE\" character varying(10) NOT NULL, CONSTRAINT \"ID\" PRIMARY KEY (\"ID\") )")
    void createAddressTable();

    @SqlUpdate("INSERT into PUBLIC.ADDRESS { ID, COUNTRY, CITY, STREET, POST_CODE } VALUES ( :id, :country, :city, :street, :postCode)")
    void add(@Bind("id") Long id, @Bind("country") String country,
             @Bind("city") String city, @Bind("street") String street,
             @Bind("postCode") String postCode);

    @SqlQuery("SELECT * FROM PUBLIC.ADDRESS WHERE ID = :id")
    Address findById(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM PUBLIC.ADDRESS WHERE COUNTRY = :country")
    Address findByCountry(@Bind("country") String country);

    @SqlQuery("DELETE * FROM PUBLIC.ADDRESS WHERE ID = :id")
    void remove(@Bind("id") Long id);

    void close();
}
