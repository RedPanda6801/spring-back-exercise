package com.study.demo.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    @ManyToOne
    private User user;
}
