package com.mbronshteyn.quarkus.bl;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DatabaseConnector {

    static private PGConnectionPoolDataSource ds;

    /**
     * Jdbi instances are thread-safe and do not own any database resources.
     * <p>
     * Typically applications create a single, shared Jdbi instance,
     * and set up any common configuration there.
     * See Configuration for more details.
     */
    private static Jdbi jdbi;

    private DatabaseConnector() {
    }

    public static Jdbi getJdbi() throws Exception {
        if (jdbi == null) {
            // make sure we don't have race condition here
            synchronized (DatabaseConnector.class) {
                // make sure there is no second thread was waiting on a lock
                if (jdbi == null) {
                    jdbi = Jdbi.create(PostgresDataSource.getDataSource())
                            .installPlugin(new SqlObjectPlugin())
                            .installPlugin(new PostgresPlugin());
                }
            }
        }
        return jdbi;
    }
}
