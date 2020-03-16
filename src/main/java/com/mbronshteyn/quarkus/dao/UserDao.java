package com.mbronshteyn.quarkus.dao;

import com.mbronshteyn.quarkus.entity.User;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDao extends CrudDao<User, Long >{

    @SqlUpdate("CREATE TABLE user (id INTEGER PRIMARY KEY, name VARCHAR)")
    public void createTable();
}
