package com.mbronshteyn.quarkus.dao;

import com.mbronshteyn.quarkus.entity.EmplProj;
import com.mbronshteyn.quarkus.entity.Project;
import com.mbronshteyn.quarkus.entity.mapping.ProjectMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(ProjectMapper.class)
public interface ProjectDao {

    @SqlUpdate("CREATE TABLE \"PROJECT\" ( \"ID\" bigint NOT NULL, \"TITLE\" character varying(255) NOT NULL, " +
            "CONSTRAINT \"PROJECT_pkey\" PRIMARY KEY (\"ID\") )")
    void createProjectTable();

    @SqlUpdate("INSERT into PUBLIC.PROJECT { ID, TITLE } " +
            "VALUES ( :id, :title)")
    void add(@Bind("id") Long id, @Bind("title") String title );

    @SqlQuery("SELECT * FROM PUBLIC.PROJECT WHERE ID = :id")
    Project findById(@Bind("id") Long id );

    @SqlQuery("SELECT * FROM PUBLIC.PROJECT WHERE TITLE = :title")
    Project findByTitle(@Bind("title") String title );

    @SqlQuery("DELETE * FROM PUBLIC.PROJECT WHERE ID = :id")
    void remove( @Bind("id") Long id );

    /**
     * Make sure we close the connection !!!
     */
    void close();
}
