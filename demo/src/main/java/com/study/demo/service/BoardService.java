package com.study.demo.service;

import com.study.demo.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.study.demo.repository.BoardRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성
    public void write(Board board){
        System.out.println(board.getId());
        boardRepository.save(board);
    }

    // 게시물 리스트 처리
    public List<Board> boardList(){
        return boardRepository.findAll();
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id){

        // Optional 값으로 데이터를 받아오기 때문에 get으로 받아와야함
        return boardRepository.findById(id).get();
    }
}
