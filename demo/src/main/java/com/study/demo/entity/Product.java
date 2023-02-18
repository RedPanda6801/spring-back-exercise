package com.study.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer price;

    private String description;

    private Integer amount; // BigDecimal class 사용 시 DB에 String으로 저장

    @Embedded
    private ProductImage image;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Product(String name, Integer price, String description, User user, Integer amount){
        this.name = name;
        this.price = price;
        this.description = description;
        this.user = user;
        this.amount = amount;
    }
}
