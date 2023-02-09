package com.study.demo.controller;

import com.study.demo.dto.ProductDto;
import com.study.demo.entity.Product;
import com.study.demo.service.JWTService;
import com.study.demo.service.ProductService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    ProductService productService;
    JWTService jwtService = new JWTService();

    @GetMapping("/market/customer")
    public String marketCustomerPage(){return "marketCustomerMain";}

    @GetMapping("/market/customer/all-product")
    @ResponseBody
    public List<Product> marketGetAllProduct(HttpServletRequest request){
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;

        List<Product> productDtoList = productService.getAllProduct();
        return productDtoList;
    }


    @GetMapping("/market/customer/product-detail/{id}")
    public String marketProductDetail(Model model, @PathVariable Integer id){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productDetail";
    }
}
