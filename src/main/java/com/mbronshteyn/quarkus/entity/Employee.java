package com.mbronshteyn.quarkus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private Long addressId;
}
