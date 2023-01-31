package com.study.demo.repository;

import com.study.demo.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository <Reply, Integer> {
}
