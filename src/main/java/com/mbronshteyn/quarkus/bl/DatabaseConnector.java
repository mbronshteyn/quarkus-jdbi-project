package com.mbronshteyn.quarkus.bl;

import org.h2.jdbcx.JdbcConnectionPool;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnector {
    public Jdbi getJdbi() {
        JdbcConnectionPool ds = JdbcConnectionPool.create(
                getPropertyValue("DB_URL"),
                getPropertyValue("DB_USER_NAME"),
                getPropertyValue("DB_PASSWORD")
        );
        return Jdbi.create(ds);
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
