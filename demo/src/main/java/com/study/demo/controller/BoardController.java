package com.study.demo.controller;


import com.study.demo.entity.Board;
import com.study.demo.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.study.demo.service.BoardService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;
    private JWTService jwtService = new JWTService();

//    private JWTService jwtService = new JWTService();

    // 페이지 매핑 시 확장자(html) 제외하고 text 리턴
    @GetMapping("/board")
    public String boardWritePage() {

        return "boardWrite";
    }

    @GetMapping("/board/list")
    public String boardListPage() {
        return "boardList";
    }
    @GetMapping("/board/get-list")
    @ResponseBody
    public List<Board> boardList(HttpServletRequest request) {
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;
        // 토큰 내의 페이로드 값을 가져와야 한다.
        System.out.println(token);

        return boardService.boardList();
    }

    // 게시글 가져오는 POST 요청
    @PostMapping("/board/add-board")
    public String addBoardWrite(HttpServletRequest request, Board board) {
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;
        // board 정보 DB에 추가
        System.out.println(board.getId());
        boardService.write(board);

        return "";
    }

    @GetMapping("/board/view/{id}")
    // board/view?id=1 => 파라미터의 id로 들어감
    public String boardView(Model model, @PathVariable("id") Integer id) {
        System.out.println(id);
        model.addAttribute("board", boardService.boardView(id));
        return "boardView";
    }

    @GetMapping("/board/delete")
    public String deleteBoard(HttpServletRequest request, Integer id) {
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;

        boardService.delete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String modifyBoard(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String updateBoard(HttpServletRequest request, @PathVariable("id") Integer id, Board board) {
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;

        Board boardTmp = boardService.boardView(id);
        boardTmp.setTitle(board.getTitle());
        boardTmp.setContent(board.getContent());
        boardService.write(boardTmp);

        return "redirect:/board/list";
    }

}