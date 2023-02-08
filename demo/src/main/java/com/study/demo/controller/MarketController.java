package com.study.demo.controller;

import com.study.demo.service.JWTService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MarketController {

    JWTService jwtService = new JWTService();

    @GetMapping("/market/customer")
    public String marketCustomerPage(){return "marketCustomerMain";}

    @GetMapping("/market/seller")
    public String marketSellerPage(){return "marketSellerMain";}

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
}
