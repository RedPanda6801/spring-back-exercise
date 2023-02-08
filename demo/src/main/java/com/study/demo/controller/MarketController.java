package com.study.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MarketController {

    @GetMapping("/market/main")
    public String marketMainPage(){return "marketCustomerMain";}


}
