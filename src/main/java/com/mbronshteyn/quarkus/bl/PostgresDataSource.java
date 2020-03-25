package com.mbronshteyn.quarkus.bl;

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
    HikariConfig config = new HikariConfig();
    DataSource ds;

    /**
     * TODO: remove when no longer needed
     * DB_URL=jdbc:postgresql://0.0.0.0:5432/fruit
     * DB_USER_NAME=postgres
     * DB_PASSWORD=example
     */ {
        config.setJdbcUrl(getPropertyValue("DB_URL"));
        config.setUsername(getPropertyValue("DB_USER_NAME"));
        config.setPassword(getPropertyValue("DB_PASSWORD"));
        config.setMaximumPoolSize(6);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setConnectionTimeout(10000);
        config.setIdleTimeout(10000);
        ds = new HikariDataSource(config);
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
            System.out.println(e);
        }

        return propertyValue;
    }
}
