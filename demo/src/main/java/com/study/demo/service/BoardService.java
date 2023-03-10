package com.study.demo.service;

import com.study.demo.entity.Board;
import com.study.demo.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.study.demo.repository.BoardRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyService replyService;
    // 글 작성
    @Transactional
    public void write(Board board){

        boardRepository.save(board);
    }

    // 게시물 리스트 처리
    public List<Board> boardList(){

        return boardRepository.findAll();
    }

    // 특정 게시글 불러오기
    public Board getBoard(Integer id){
        // Optional 값으로 데이터를 받아오기 때문에 get으로 받아와야함
        return boardRepository.findById(id).get();
    }

    // 게시물 삭제
    public void delete(Integer id){
        boardRepository.deleteById(id);
    }


}
