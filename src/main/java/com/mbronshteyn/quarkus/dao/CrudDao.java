package com.mbronshteyn.quarkus.dao;

import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@UseClasspathSqlLocator
public interface CrudDao<T, ID> {
        @SqlUpdate
        void insert(T entity);

        @SqlQuery
        T getById(ID id);

        @SqlQuery
        List<T> list();

        @SqlUpdate
        void update(T entity);

        @SqlUpdate
        void delete(ID id);
}
