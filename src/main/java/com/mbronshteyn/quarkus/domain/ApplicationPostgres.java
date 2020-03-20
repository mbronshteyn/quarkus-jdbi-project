package com.mbronshteyn.quarkus.domain;

import com.mbronshteyn.quarkus.entity.Fruit;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;
import java.util.UUID;

public class ApplicationPostgres {

    public static void main(String[] args) {

        // TODO: replace hardcoded credentials with config parameters
        Jdbi jdbi = Jdbi.create("jdbc:postgresql://0.0.0.0:5432/fruit",
                "postgres", "example")
                .installPlugin(new PostgresPlugin())
                .installPlugin(new SqlObjectPlugin());

        // example of using withHandle
        List<Fruit> fruits = jdbi.withHandle(handle -> {

            // cleanup
            handle.createUpdate("DELETE from fruit")
                    .execute();

            // Inline positional parameters
            handle.execute("INSERT INTO fruit( uuid, name, description) VALUES ( ?, ?, ?)",
                    UUID.randomUUID().toString(), "Apple", "Fall Fruit");

            // Positional parameters
            handle.createUpdate("INSERT INTO fruit( uuid, name, description ) VALUES ( ?, ?, ?)")
                    .bind(0, UUID.randomUUID().toString()) // 0-based parameter indexes
                    .bind(1, "Guava")
                    .bind(2, "Tropical Fruit")
                    .execute();

            // Easy mapping to any type
            List<Fruit> fruitList = handle.createQuery("SELECT * FROM fruit ORDER BY name")
                    .mapToBean(Fruit.class)
                    .list();

            handle.close();

            return fruitList;
        });

        fruits.forEach(System.out::println);
    }
}
