package com.study.demo.service;

import com.study.demo.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.study.demo.repository.BoardRepository;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board){
        System.out.println(board.getId());
        boardRepository.save(board);
    }

    public List<Board> boardList(){
        return boardRepository.findAll();
    }
}
