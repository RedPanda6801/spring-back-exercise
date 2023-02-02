package com.study.demo.controller;


import com.study.demo.dto.BoardDto;
import com.study.demo.entity.Board;
import com.study.demo.entity.User;
import com.study.demo.exception.ForbiddenException;
import com.study.demo.service.JWTService;
import com.study.demo.service.ReplyService;
import com.study.demo.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.study.demo.service.BoardService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserService userService;
    private JWTService jwtService = new JWTService();

    // 페이지 매핑 시 확장자(html) 제외하고 text 리턴
    @GetMapping("/board/write")
    public String boardWritePage() {

        return "boardWrite";
    }

    @GetMapping("/board/list")
    public String boardListPage() {
        return "boardList";
    }

    @GetMapping("/board/get-list")
    @ResponseBody
    public List<BoardDto> boardList(HttpServletRequest request) {
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) {
            throw new ForbiddenException("403 Forbidden");
        }
        System.out.println(token);
        // 토큰 내의 페이로드 값을 가져와야 한다.
        List<BoardDto> boardDtos = boardService.boardList().stream().map(BoardDto::new)
                .collect(Collectors.toList());
        return boardDtos;
    }


    // 게시글 가져오는 POST 요청
    @PostMapping("/board/add-board")
    @ResponseBody
    public String addBoardWrite(@RequestBody BoardDto boardDto, HttpServletRequest request) {
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;
        // board 정보 DB에 추가
        String userid = token.get("userId").toString();
        User user = userService.getUser(userid);
        Board board = Board.builder()
                .content(boardDto.getContent())
                .title(boardDto.getTitle())
                .user(user)
                .build();
        try {
            boardService.write(board);
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
        return "success";
    }

    @GetMapping("/board/view/{id}")
    // board/view?id=1 => 파라미터의 id로 들어감
    // reply 또한 이 컨트롤러에서 호출하도록 함
    public String boardView(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("board", boardService.getBoard(id));
        model.addAttribute("replys", replyService.getReplys(id));
        return "boardView";
    }

    @GetMapping("/board/delete/{id}")
    @ResponseBody
    public String deleteBoard(HttpServletRequest request, @PathVariable Integer id) {
        // 로그인 정보 확인
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;
        String writerId = boardService.getBoard(id).getUser().getUserid();
        if(!writerId.equals(token.get("userId").toString())){
            return null;
        }
        boardService.delete(id);
        return "success";
    }

    @GetMapping("/board/modify/{id}")
    public String modifyBoard(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.getBoard(id));
        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    @ResponseBody
    public String updateBoard(HttpServletRequest request, @PathVariable("id") Integer id, @RequestBody BoardDto boardDto) {
        Claims token = jwtService.checkAuthorizationHeader(request);
        if(token == null) return null;
        Board boardTmp = boardService.getBoard(id);
        // 로그인 사용자와 게시글 작성자 비교
        String loginUser = token.get("userId").toString();
        if(!loginUser.equals(boardTmp.getUser().getUserid())) return null;
        // DB에서 수정 작업
        try{
            boardTmp.update(boardDto.getTitle(), boardDto.getContent());
            boardService.write(boardTmp);
            return "success";
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

}