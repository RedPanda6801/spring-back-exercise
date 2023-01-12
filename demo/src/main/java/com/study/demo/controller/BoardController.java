package com.study.demo.controller;


import com.study.demo.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.study.demo.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 페이지 매핑 시 확장자(html) 제외하고 text 리턴
    @GetMapping("/board")
    public String boardWriteForm(){
        return "boardWrite";
    }

    // 값 리턴 시에 responsebody를 달아 준다
    @GetMapping("/")
    @ResponseBody
    public String main(){
        return "Hello World";
    }

    // 게시글 가져오는 POST 요청
    @PostMapping("/board/add-board")
    public String addBoardWrite(Board board){

        System.out.println(board.getId());
        boardService.write(board);

        return "";
    }

    @GetMapping("/board/get-list")
    public String boardList(Model model){
        model.addAttribute("list", boardService.boardList());
        return "boardList";
    }
}
