package com.study.demo.controller;


import com.study.demo.dto.ReplyDto;
import com.study.demo.entity.Board;
import com.study.demo.entity.Reply;
import com.study.demo.entity.User;
import com.study.demo.service.BoardService;
import com.study.demo.service.JWTService;
import com.study.demo.service.ReplyService;
import com.study.demo.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReplyController {


    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyService replyService;
    private JWTService jwtService = new JWTService();

    @PostMapping("/reply/add-reply")
    @ResponseBody
    public String addReply(@RequestBody ReplyDto replyDto, HttpServletRequest request){
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;
        // 저장할 유저 값과 게시글 값을 dto에서 가져옴
        String userid = token.get("userId").toString();
        int boardid = replyDto.getBoardid();
        // DB에서 각각 검색
        User user = userService.getUser(userid);
        Board board = boardService.getBoard(boardid);
        // Reply로 변환
        Reply reply = Reply.builder()
                .content(replyDto.getContent())
                .board(board)
                .user(user)
                .build();
        try{
            // DB에 추가
            replyService.write(reply);
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
        return "success";
    }
}
