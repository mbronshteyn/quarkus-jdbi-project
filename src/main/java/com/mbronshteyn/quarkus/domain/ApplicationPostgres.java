package com.mbronshteyn.quarkus.domain;

public class ApplicationPostgres {

//    public static void main(String[] args) throws Exception {
//
//        // Get a connection from the Connection Pool
//        Jdbi jdbi = DatabaseConnector.getJdbi();
//
//        // example of using withHandle
//        List<Fruit> fruits = jdbi.withHandle(handle -> {
//
//            // cleanup
//            handle.createUpdate("DELETE from fruit")
//                    .execute();
//
//            // Inline positional parameters
//            handle.execute("INSERT INTO fruit( uuid, name, description) VALUES ( ?, ?, ?)",
//                    UUID.randomUUID().toString(), "Apple", "Fall Fruit");
//
//            // Positional parameters
//            handle.createUpdate("INSERT INTO fruit( uuid, name, description ) VALUES ( ?, ?, ?)")
//                    .bind(0, UUID.randomUUID().toString()) // 0-based parameter indexes
//                    .bind(1, "Guava")
//                    .bind(2, "Tropical Fruit")
//                    .execute();
//
//            // Easy mapping to any type
//            List<Fruit> fruitList = handle.createQuery("SELECT * FROM fruit ORDER BY name")
//                    .mapToBean(Fruit.class)
//                    .list();
//
//            return fruitList;
//        });
//
//        fruits.forEach(fruit -> {
//            System.out.println("Fruits #1: " + fruit);
//        });
//
//        // Using DAO interface
//        FruitDao fruitDao = jdbi.onDemand(FruitDao.class);
//        fruitDao.add(UUID.randomUUID().toString(), "Watermelon", "Not a fruit");
//        fruits = fruitDao.findAll();
//
//        fruits.forEach(fruit -> {
//            System.out.println("Fruits #2: " + fruit);
//        });
//
//        jdbi.useHandle(Handle::close);
//
//    }
}

