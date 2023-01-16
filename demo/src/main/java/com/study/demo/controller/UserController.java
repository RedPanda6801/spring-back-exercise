package com.study.demo.controller;

import com.study.demo.entity.User;
import com.study.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/sign")
    public String signUpPage() {

        return "signup";
    }

    @PostMapping("/sign/sign-up")
    public String signUp(User user) {

        // userid의 중복 확인하여 다시 회원가입 페이지로 돌아옴
        User userTmp = userService.getUser(user.getUserid());
        if(userTmp != null){
            return "redirect:/sign";
        }
        Boolean isCreated = userService.createUser(user);
        if(!isCreated){
            // DB 저장 도중에 예외가 발생하면 다시 돌아감
            // 알람 띄워주는 기능 추가 필요
            return "redirect:/sign";
        }
        // redirect 시에 URL로 리턴됨
        return "redirect:/login";
    }
}
