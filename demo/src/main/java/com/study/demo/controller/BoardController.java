package com.study.demo.controller;


import com.study.demo.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.study.demo.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 값 리턴 시에 responsebody를 달아 준다
    @GetMapping("/")
    @ResponseBody
    public String main() {
        String hello = "Hello World";
        return hello;
    }

    // 페이지 매핑 시 확장자(html) 제외하고 text 리턴
    @GetMapping("/board")
    public String boardWriteForm() {
        return "boardWrite";
    }

    @GetMapping("/board/list")
    public String boardList(Model model) {
        model.addAttribute("list", boardService.boardList());
        return "boardList";
    }

    // 게시글 가져오는 POST 요청
    @PostMapping("/board/add-board")
    public String addBoardWrite(Board board) {

        System.out.println(board.getId());
        boardService.write(board);

        return "";
    }

    @GetMapping("/board/view")
    // board/view?id=1 => 파라미터의 id로 들어감
    public String boardView(Model model, Integer id) {

        model.addAttribute("board", boardService.boardView(id));
        return "boardView";
    }

    @GetMapping("/board/delete")
    public String deleteBoard(Integer id) {
        boardService.delete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String modifyBoard(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String updateBoard(@PathVariable("id") Integer id, Board board) {

        Board boardTmp = boardService.boardView(id);
        boardTmp.setTitle(board.getTitle());
        boardTmp.setContent(board.getContent());
        boardService.write(boardTmp);

        return "redirect:/board/list";
    }

}