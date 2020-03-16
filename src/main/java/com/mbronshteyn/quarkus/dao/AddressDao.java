package com.mbronshteyn.quarkus.dao;

import com.mbronshteyn.quarkus.entity.Address;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;

@RegisterBeanMapper(Address.class)
public interface AddressDao extends CrudDao<Address, Long> { }
