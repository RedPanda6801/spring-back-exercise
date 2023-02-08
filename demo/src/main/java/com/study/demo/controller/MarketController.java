package com.study.demo.controller;

import com.study.demo.dto.BoardDto;
import com.study.demo.dto.ProductDto;
import com.study.demo.entity.Product;
import com.study.demo.entity.User;
import com.study.demo.service.JWTService;
import com.study.demo.service.ProductService;
import com.study.demo.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MarketController {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    JWTService jwtService = new JWTService();

    @GetMapping("/market/customer")
    public String marketCustomerPage(){return "marketCustomerMain";}

    @GetMapping("/market/seller")
    public String marketSellerPage(){return "marketSellerMain";}

    @GetMapping("/market/seller/add-page")
    public String marketSellerAddPage(){return "addProductForm";}

    @GetMapping("/market/main")
    @ResponseBody
    public String marketMainPage(HttpServletRequest request){
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;
        // 권한 확인
        String role = token.get("role").toString();
        if(role.equals("customer")) {
            return "customer";
        }else{
            return "seller";
        }
    }

    @PostMapping("/market/seller/add-product")
    @ResponseBody
    public String marketAddProduct(HttpServletRequest request, @ModelAttribute ProductDto productDto){
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;

        User user = userService.getUser(token.get("userId").toString());

        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .user(user)
                .build();
        System.out.println(product);
        try{
            productService.write(product);
            return "success";
        }catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/market/seller/get-my-product")
    @ResponseBody
    public List<ProductDto> marketGetMyProduct(HttpServletRequest request){
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;

        Integer id = Integer.parseInt(token.get("id").toString());

        List<ProductDto> productDtos = productService.myProductList(id).stream().map(ProductDto::new).collect(Collectors.toList());

        return productDtos;
    }
}

// formdata https://velog.io/@josworks27/formData-console.log
// axios https://doogle.link/axios-%EC%82%AC%EC%9A%A9%EC%8B%9C-%ED%8F%BC-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EC%A0%84%EC%86%A1%ED%95%98%EA%B8%B0-%ED%8C%8C%EC%9D%BC-%EC%97%85%EB%A1%9C%EB%93%9C/
// multiform-data https://blogpack.tistory.com/1088
// image 크기 조절 https://velog.io/@jinho_pca/Spring-Boot-%ED%8C%8C%EC%9D%BC-%EC%97%85%EB%A1%9C%EB%93%9C-%EC%9A%A9%EB%9F%89%EC%A0%9C%ED%95%9C-%EC%84%A4%EC%A0%95
// ModelAttribute https://mopil.tistory.com/m/69