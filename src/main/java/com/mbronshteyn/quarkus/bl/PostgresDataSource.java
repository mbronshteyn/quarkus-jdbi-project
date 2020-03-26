package com.mbronshteyn.quarkus.bl;

import com.google.common.flogger.FluentLogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.inject.Singleton;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
public class PostgresDataSource {

    /**
     * HikariCP. HikariCP is a very fast lightweight Java connection pool.
     * The API and overall codebase are relatively small (a good thing) and highly optimized.
     * It also does not cut corners for performance like many other
     * Java connection pool implementations.
     * The Wiki is highly informative and dives really deep
     */
    DataSource ds;

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public PostgresDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(getPropertyValue("DB_URL"));
        config.setUsername(getPropertyValue("DB_USER_NAME"));
        config.setPassword(getPropertyValue("DB_PASSWORD"));
        config.setMaximumPoolSize(6);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setConnectionTimeout(10000);
        config.setIdleTimeout(10000);
        try {
            ds = new HikariDataSource(config);
        } catch (Throwable th) {
            logger.atSevere().log("init ds: %s", th);
        }
    }

    public DataSource getDataSource() {
        return ds;
    }

    public static String getPropertyValue(String propertyName) {
        String propertyValue = "";
        Properties properties = new Properties();

        try (InputStream inputStream = PostgresDataSource.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
            propertyValue = properties.getProperty(propertyName);
        } catch (IOException e) {
            // use System.out.println for now
            logger.atSevere().log( "Error reading properties: %s", e );
        }

        return propertyValue;
    }
}
