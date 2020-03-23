package com.mbronshteyn.quarkus.bl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.jdbcx.JdbcConnectionPool;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ApplicationScoped
public class DatabaseConnector {

    static private PGConnectionPoolDataSource ds;

    // TODO: find out how thread safe it is
    private Jdbi jdbi;

    public DatabaseConnector() throws Exception {
        ds = new PGConnectionPoolDataSource();
        ds.setUrl(getPropertyValue("DB_URL"));
        ds.setUser(getPropertyValue("DB_USER_NAME"));
        ds.setPassword(getPropertyValue("DB_PASSWORD"));

        jdbi = Jdbi.create(ds.getConnection())
                .installPlugin(new PostgresPlugin())
                .installPlugin(new SqlObjectPlugin());
    }

    // TODO: revisit later
    public Jdbi getConnection() throws Exception {
        return jdbi;
    }

    // TODO: find out if there is a way to use it
    // and minimize setup
    public Jdbi getHickariCP() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setLoadBalanceHosts(true);
        String[] serverNames = {"0.0.0.0"};
        ds.setServerNames(serverNames);
        HikariConfig hc = new HikariConfig();
        hc.setJdbcUrl(getPropertyValue("DB_URL"));
        hc.setUsername(getPropertyValue("DB_USER_NAME"));
        hc.setPassword(getPropertyValue("DB_PASSWORD"));
        hc.setDataSource(ds);
        hc.setMaximumPoolSize(6);
        return Jdbi.create(new HikariDataSource(hc))
                .installPlugin(new SqlObjectPlugin())
                .installPlugin(new PostgresPlugin());
    }


    public Jdbi getJdbi() {
        JdbcConnectionPool ds = JdbcConnectionPool.create(
                getPropertyValue("DB_URL"),
                getPropertyValue("DB_USER_NAME"),
                getPropertyValue("DB_PASSWORD")
        );

        return Jdbi.create(ds)
                .installPlugin(new PostgresPlugin())
                .installPlugin(new SqlObjectPlugin());
    }


    public String getPropertyValue(String propertyName) {
        String propertyValue = "";
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
            propertyValue = properties.getProperty(propertyName);
        } catch (IOException e) {
            // use System.out.println for now
            System.out.println(e);
        }

        return propertyValue;
    }

}
