package com.ssafy.edu.service;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.edu.dto.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {
	private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.expmin}")
    private Long expireMin;
    
    //토큰 생성
    public String create(final Member member) {
    	log.trace("time: {}", expireMin);
        final JwtBuilder builder = Jwts.builder();
        // JWT Token = Header + Payload + Signagure

        builder.setHeaderParam("typ", "JWT");// 토큰의 타입으로 고정 값

        // Payload 설정 - claim 정보 포함
        builder.setSubject(member.getTokenname())// 토큰 제목 설정
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))// 유효기간
                .claim("data", member);

        // signature - secret key를 이용한 암호화
        builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

        // 마지막 직렬화 처리
        final String token = builder.compact();
        log.info(member.getTokenname() + " 발행: {}", token);
        return token;
    }
    
    //토큰 체크
    public void checkValid(final String token) {
    	log.info("토큰 점검: {}", token);
        Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(token);
    }

     //토큰 분석
    public Map<String, Object> get(final String token) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(token);
        } catch (final Exception e) {
            throw new RuntimeException();
        }

        log.info("claims: {}", claims);

        // Claims는 Map의 구현체이다.
        return claims.getBody();
    }
}