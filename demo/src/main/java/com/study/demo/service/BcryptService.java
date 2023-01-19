package com.study.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BcryptService {

    public String encodeBcrypt(String planeText, int strength) {
        return new BCryptPasswordEncoder(strength).encode(planeText);
    }
}
