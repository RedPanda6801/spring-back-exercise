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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @PostMapping("/reply/update/{id}")
    @ResponseBody
    public String updateReply(@RequestBody ReplyDto replyDto, HttpServletRequest request, @PathVariable Integer id){
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;
        // 예전 Reply 정보를 가져오기
        Reply replyTmp = replyService.getReply(id);
        // 로그인 사용자와 게시글 작성자 비교
        String loginUser = token.get("userId").toString();
        if(!loginUser.equals(replyTmp.getUser().getUserid())) return null;
        // 댓글 수정
        try{
            replyTmp.update(replyDto.getContent());
            replyService.write(replyTmp);
            return "success";
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/reply/delete/{id}")
    @ResponseBody
    public String deleteReply(@PathVariable Integer id, HttpServletRequest request){
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;
        // 예전 Reply 정보를 가져오기
        Reply reply = replyService.getReply(id);
        // 로그인 사용자와 게시글 작성자 비교
        String loginUser = token.get("userId").toString();
        if(!loginUser.equals(reply.getUser().getUserid())) return null;
        // 댓글 수정
        try{
            replyService.delete(id);
            return "success";
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
}
