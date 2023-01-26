package com.study.demo.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userid;

    private String name;

    private String password;

    private String phone;

    @OneToMany(mappedBy = "user")
    List<Board> boards = new ArrayList<>();
}
