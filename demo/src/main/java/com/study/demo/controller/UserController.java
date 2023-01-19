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

    @GetMapping("/")
    // Token이 없으면 로그인 화면으로 이동하도록 해야함
    public String main() {
        // 토큰 확인하는 변수
        boolean isLoggedIn = true;

        if(!isLoggedIn){
            return "redirect:/auth/login";
        }else {
            return "redirect:/board";
        }
    }
    // 페이지 매핑
    // 회원가입 화면으로 redirect
    @GetMapping("/auth/sign")
    public String signUpForm() { return "signup"; }
    // 로그인화면으로 redirect
    @GetMapping("/auth/login")
    public String loginForm(){
        return "login";
    }


    // 로그인 작업 수행
    @PostMapping("/auth/login")
    public String loginCheck(User user){
        // 입력된 정보가 없으면 되돌아감 -> input값으로 받은 데이터는 null이 아닌 것 같다
        if(user.getUserid() == "" || user.getPassword() == ""){
            return "redirect:/auth/login";
        }
        // 유저 정보를 DB에서 확인
        boolean isUser = userService.userAuth(user);
        // login fail
        if(!isUser){
            return "redirect:/auth/login";
        }
        return "redirect:/";

    }
    @PostMapping("/auth/sign")
    public String signUp(User user) {

        // userid의 중복 확인하여 다시 회원가입 페이지로 돌아옴
        User userTmp = userService.getUser(user.getUserid());
        if(userTmp != null){
            return "redirect:/auth/sign";
        }
        boolean isCreated = userService.createUser(user);
        if(!isCreated){
            // DB 저장 도중에 예외가 발생하면 다시 돌아감
            return "redirect:/auth/sign";
        }
        // redirect 시에 URL로 리턴됨
        return "redirect:/auth/login";
    }
}
