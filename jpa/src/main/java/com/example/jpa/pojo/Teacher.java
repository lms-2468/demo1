package com.example.jpa.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private int age;

    @OneToMany(mappedBy = "tid", fetch = FetchType.EAGER)
    private List<Student> students;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "tb_teacher_role"
//            , joinColumns = {@JoinColumn(name = "t_id", referencedColumnName = "id")}
//            , inverseJoinColumns = {@JoinColumn(name = "r_id", referencedColumnName = "id")})
//    private List<Role> roles;
}
