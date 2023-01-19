package com.study.demo.service;

import com.study.demo.entity.User;
import com.study.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BcryptService bcryptService = new BcryptService();

    public User getUser(String userid){
        User userTmp = userRepository.findByUserid(userid);

        if(userTmp == null){
            return null;
        }
        return userTmp;
    }
    // 생성 시에 비밀번호의 해시화가 필요하다.
    public boolean createUser(User user){
        try{
            User userTmp = user;
            String hash = bcryptService.encodeBcrypt(user.getPassword(), 10);
            System.out.println(hash);
            userTmp.setPassword(hash);
            userRepository.save(user);
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    // 로그인 확인
    public boolean userAuth(User user){
        try{
            String userid = user.getUserid();
            String pass = user.getPassword();
            // 아이디로 유저 찾기
            User userTmp = userRepository.findByUserid(userid);
            // 아이디가 DB에 없을 시에 예외 처리
            if(userTmp == null){
                return false;
            }
            // 비밀번호 불일치시 예외처리 - String Object는 equals()로 비교해야함
            if(!pass.equals(userTmp.getPassword())){
                return false;
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
