package com.study.demo.service;

import com.study.demo.entity.User;
import com.study.demo.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(String userid){
        User userTmp = userRepository.findByUserid(userid);

        if(userTmp == null){
            return null;
        }
        return userTmp;
    }
    public Boolean createUser(User user){
        try{
            userRepository.save(user);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
