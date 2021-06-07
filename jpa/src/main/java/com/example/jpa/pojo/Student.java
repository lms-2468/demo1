package com.example.jpa.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private int age;
    @Column
    private String tid;
}
