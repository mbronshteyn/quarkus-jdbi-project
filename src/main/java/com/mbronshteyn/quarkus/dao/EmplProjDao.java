package com.mbronshteyn.quarkus.dao;

import com.mbronshteyn.quarkus.entity.EmplProj;
import com.mbronshteyn.quarkus.entity.mapping.EmplProjMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(EmplProjMapper.class)
public interface EmplProjDao {

    @SqlUpdate("CREATE TABLE \"EMPL_PROJ\" " +
            "( \"EMPLOYEE_ID\" bigint NOT NULL, \"PROJECT_ID\" bigint NOT NULL, " +
            "CONSTRAINT \"EMPL_PROJ_EMPLOYEE_ID_fkey\" FOREIGN KEY (\"EMPLOYEE_ID\") REFERENCES \"EMPLOYEE\" (\"ID\"), " +
            "CONSTRAINT \"EMPL_PROJ_PROJECT_ID_fkey\" FOREIGN KEY (\"PROJECT_ID\") REFERENCES \"PROJECT\" (\"ID\") )")
    void createEmplProjTable();

    @SqlUpdate("INSERT into PUBLIC.EMPL_PROJ { EMPLOYEE_ID, PROJECT_ID } " +
            "VALUES ( :employeeId, :projectId)")
    void add( @Bind("employeeId") Long employeeId, @Bind("projectId") Long projectId );

    @SqlQuery("SELECT * FROM PUBLIC.EMPLOYEE WHERE EMPLOYEE_ID = :employeeId AND PROJECT_ID = :projectId")
    EmplProj findById(@Bind("employeeId") Long employeeId, @Bind("projectId") Long projectId );

    @SqlQuery("DELETE * FROM PUBLIC.EMPLOYEE WHERE EMPLOYEE_ID = :employeeId AND PROJECT_ID = :projectId")
    void remove( @Bind("employeeId") Long employeeId, @Bind("projectId") Long projectId );

    /**
     * Make sure we close the connection !!!
     */
    void close();
}
