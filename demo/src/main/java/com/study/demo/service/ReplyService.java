package com.study.demo.service;

import com.study.demo.entity.Reply;
import com.study.demo.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void write(Reply reply){
        replyRepository.save(reply);
    }
}
