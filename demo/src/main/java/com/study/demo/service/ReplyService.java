package com.study.demo.service;

import com.study.demo.entity.Reply;
import com.study.demo.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void write(Reply reply){
        replyRepository.save(reply);
    }
    public void delete(Integer id) {replyRepository.deleteById(id);}
    // 특정 게시글 불러오기
    public List<Reply> getReplys(Integer id){
        // Optional 값으로 데이터를 받아오기 때문에 get으로 받아와야함
        return replyRepository.findAllByBoardId(id);
    }
    public Reply getReply(Integer id){
        return replyRepository.findById(id).get();
    }


}
