package com.study.demo.service;

import com.study.demo.dto.UserDto;
import com.study.demo.entity.User;
import com.study.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private BcryptService bcryptService = new BcryptService();
    private JWTService jwtService = new JWTService();

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
            userTmp.setPassword(hash);
            userRepository.save(user);
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    // 로그인 확인
    public String userAuth(UserDto userDto){
        try{
            String userid = userDto.getUserid();
            String pass = userDto.getPassword();
            // 아이디로 유저 찾기
            User userTmp = userRepository.findByUserid(userid);
            // 아이디가 DB에 없을 시에 예외 처리
            if(userTmp == null){
                System.out.println("user can't find");
                return null;
            }
            // 비밀번호 불일치시 예외처리 - hash화된 비밀번호를 bcrypt로 비교
            boolean hashCheck = bcryptService.matchesBcrypt(pass, userTmp.getPassword(), 10);
            System.out.printf("해시값 비교 성공 : %s",hashCheck);
            if(!hashCheck){
                return null;
            }
            // 모든 예외처리 통과시 로그인 성공
            String token = jwtService.makeJwtToken(userTmp.getId(), userid, userTmp.getRole());
            System.out.printf("부여받은 토큰 : %s%n", token);

            // Model에 token을 같이 넘겨주어 프론트에서 localStorage에 저장하게끔 구현

            return token;
        }catch(Exception e){
            return null;
        }
    }
}
