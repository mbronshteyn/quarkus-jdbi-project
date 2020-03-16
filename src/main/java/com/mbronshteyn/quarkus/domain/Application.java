package com.mbronshteyn.quarkus.domain;

import com.mbronshteyn.quarkus.entity.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;

public class Application {

    public static void main(String[] args) {

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        Handle jdbiHandle = jdbi.open();

        // example of using withHandle
        List<User> users = jdbi.withHandle(handle -> {
            handle.execute("CREATE TABLE user (id INTEGER PRIMARY KEY, name VARCHAR)");

            // Inline positional parameters
            handle.execute("INSERT INTO user(id, name) VALUES (?, ?)", 0, "Alice");

            // Positional parameters
            handle.createUpdate("INSERT INTO user(id, name) VALUES (?, ?)")
                    .bind(0, 1) // 0-based parameter indexes
                    .bind(1, "Bob")
                    .execute();

            // Named parameters
            handle.createUpdate("INSERT INTO user(id, name) VALUES (:id, :name)")
                    .bind("id", 2)
                    .bind("name", "Clarice")
                    .execute();

            // Named parameters from bean properties
            handle.createUpdate("INSERT INTO user(id, name) VALUES (:id, :name)")
                    .bindBean(new User(3, "David"))
                    .execute();

            // Easy mapping to any type
            List<User> userList = handle.createQuery("SELECT * FROM user ORDER BY name")
                    .mapToBean(User.class)
                    .list();

            handle.close();

            return userList;
        });

        users.forEach(System.out::println);

        // TODO: work in progress
//        Address address = new Address();
//        address.setId(1L);
//        address.setCountry("DC");
//        address.setCity("Gotham City");
//        address.setStreet("Arkham street 1");
//        address.setPostCode("12345");
//
//        Employee employee = new Employee();
//        employee.setId(1L);
//        employee.setFirstName("James");
//        employee.setLastName("Gordon");
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(1939, Calendar.MAY, 1);
//
//        employee.setBirthday(new java.sql.Date(calendar.getTime().getTime()));
//        employee.setAddressId(address.getId());
//
//        Project project = new Project();
//        project.setId(1L);
//        project.setTitle("Gotham City Police Department Commissioner");
//
//        EmplProj emplProj = new EmplProj();
//        emplProj.setEmployeeId(employee.getId());
//        emplProj.setProjectId(project.getId());

//        try {
//            // create table
//            List<Address> addresses = jdbi.withExtension(AddressDao.class, dao -> {
//                dao.createAddressTable();
//                dao.add(1L, "USA", "San Diego", "1234 Street", "92000");
//                return dao.findAll();
//            });
//
//            addresses.forEach( System.out::println );
//
//        } catch ( Exception e ) {
//            e.printStackTrace();
//        }
    }
}
