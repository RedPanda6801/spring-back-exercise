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
import java.util.stream.Collectors;

@Controller
public class SellerController {

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    ProductService productService;

    JWTService jwtService = new JWTService();

    @GetMapping("/market/seller")
    public String marketSellerPage(){return "marketSellerMain";}

    @GetMapping("/market/seller/add-page")
    public String marketSellerAddPage(){return "addProductForm";}

    @GetMapping("/market/seller/recipe/{id}")
    public String marketSellerRecipePage(Model model, @PathVariable Integer id){
        List<Recipe> recipes = recipeService.getAllRecipeByProductId(id);
        // 없는 레시피에 대한 주문 내역 예외처리 필요
        model.addAttribute("recipes", recipes);
        return "recipeDetail";
    }

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
                .amount(productDto.getAmount())
                .user(user)
                .build();
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

    @PostMapping("/market/seller/recipe/accept")
    @ResponseBody
    public String marketRecipeAccept(HttpServletRequest request, @RequestBody RecipeDto recipeDto){
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;

        System.out.println(recipeDto.getId());
        return "success";
    }
}