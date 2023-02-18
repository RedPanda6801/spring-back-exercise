package com.study.demo.controller;

import com.study.demo.dto.ProductDto;
import com.study.demo.dto.RecipeDto;
import com.study.demo.entity.Product;
import com.study.demo.entity.Recipe;
import com.study.demo.entity.User;
import com.study.demo.service.JWTService;
import com.study.demo.service.ProductService;
import com.study.demo.service.RecipeService;
import com.study.demo.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    RecipeService recipeService;
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
        ProductDto productDto = new ProductDto(product);

        model.addAttribute("product", productDto);
        return "productDetail";
    }

    @PostMapping("/market/customer/product-detail/order")
    @ResponseBody
    public String marketProductOrder(HttpServletRequest request, @RequestBody RecipeDto recipeDto){
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;

        String userId = token.get("userId").toString();
        User user = userService.getUser(userId);
        Product product = productService.getProductById(recipeDto.getProductid());

        Recipe recipe = Recipe.builder()
                .description(recipeDto.getDescription())
                .amount(recipeDto.getAmount())
                .user(user)
                .product(product)
                .build();

        recipeService.write(recipe);
        return "success";
    }
}
