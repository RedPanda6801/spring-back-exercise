package com.study.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Product> products = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Board(String title, String content, User user){
        this.content = content;
        this.title = title;
        this.user = user;
    }

    public void update(String title, String content){
        this.content = content;
        this.title = title;
    }
}
