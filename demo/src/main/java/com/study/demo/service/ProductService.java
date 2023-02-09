package com.study.demo.service;

import com.study.demo.dto.ProductDto;
import com.study.demo.entity.Product;
import com.study.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void write(Product product){
        productRepository.save(product);
    }
    public List<Product> myProductList(Integer id){
        return productRepository.findByUserId(id);
    }

    public List<Product> getAllProduct(){return productRepository.findAll();}

    public Product getProductById(Integer id){return productRepository.findById(id).get();}
}
