package com.study.demo.service;

import io.jsonwebtoken.*;
import org.springframework.http.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;

public class JWTService {

    public String makeJwtToken(Integer id, String userid) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 타입 지정
                .setIssuer("redpanda") // issuer 설정
                .setIssuedAt(now) // 발급 시간 설정 (Date 객체만 가능)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // 만료 시 설정 (Date 객체만 가능)
                .claim("userId", userid) // 비공개 클래임 설정 가능
                .signWith(SignatureAlgorithm.HS256, "secret") // 시크릿 키 설정
                .compact();
    }


    private Claims parseJwtToken(String authorizationHeader) {
        validationAuthorizationHeader(authorizationHeader); // (1)
        String token = extractToken(authorizationHeader); // (2)

        return Jwts.parser()
                .setSigningKey("secret") // (3)
                .parseClaimsJws(token) // (4)
                .getBody();
    }

    // JWT 토큰 확인
    public Claims checkAuthorizationHeader (HttpServletRequest request){
        try{
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = authorizationHeader.split(" ")[1];
            System.out.println(token);
            if(token.equals("null")){
                return null;
            }
            return parseJwtToken(authorizationHeader);
        }catch(ExpiredJwtException e) {
            return null;
        }
    }


    private void validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException();
        }
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }
}
