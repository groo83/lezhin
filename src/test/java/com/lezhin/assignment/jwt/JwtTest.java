package com.lezhin.assignment.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt.io 페이지 Decode
 */
@SpringBootTest
public class JwtTest {

    @Test
    public void createToken() {

        // Header 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        // payload 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("KEY", "HelloWorld");
        payloads.put("NickName","jiyeong");
        payloads.put("Age","29");
        payloads.put("TempAuth","Y");

        Long expiredTime = 1000 * 60L * 60L * 1L; // 토큰 유효 시간 (1시간)

        Date date = new Date();
        date.setTime(date.getTime() + expiredTime);

        Key key = Keys.hmacShaKeyFor("MyNickNameisJiyeongAndNameisJiyeong".getBytes(StandardCharsets.UTF_8));

        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setSubject("Test") // 토큰 용도
                .setExpiration(date) // 토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact(); // 토큰 생성


        System.out.println(">> jwt : " + jwt);
    }
}