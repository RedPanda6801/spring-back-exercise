package com.study.demo.service;

import com.study.demo.entity.Product;
import com.study.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void write(Product product){
        productRepository.save(product);
    }
}
