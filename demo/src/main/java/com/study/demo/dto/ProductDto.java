package com.study.demo.dto;

import com.study.demo.entity.Board;
import com.study.demo.entity.Product;
import com.study.demo.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
    private Integer id;

    private String name;

    private Integer price;

    private String description;

    private Integer amount;

    private String userid;
    @Embedded
    private ProductImage image;

    public ProductDto(Product product){
        this.id = product.getId();
        this.name = product.getName();
        if(this.price == null) this.price = 0;
        else this.price = product.getPrice();
        this.description = product.getDescription();
        if(this.amount == null) this.amount = 0;
        else this.amount = product.getAmount();
        this.image = product.getImage();
        this.userid = product.getUser().getUserid();
    }
}
