package com.mbronshteyn.quarkus.bl;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DatabaseConnector {

    /**
     * Jdbi instances are thread-safe and do not own any database resources.
     * <p>
     * Typically applications create a single, shared Jdbi instance,
     * and set up any common configuration there.
     * See Configuration for more details.
     */
    public static Jdbi getJdbi() throws Exception {
        // TODO: monitor if we have connection problem again
        return Jdbi.create(new PostgresDataSource().getDataSource())
                .installPlugin(new SqlObjectPlugin())
                .installPlugin(new PostgresPlugin());
    }
}
