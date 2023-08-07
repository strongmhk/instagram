package com.clipper.instagram.config.jwt;

public class JwtProperties {
    static final String SECRET = "clipper"; // 우리 서버만 알고있는 비밀값
    static final int EXPIRATION_TIME = 60000 * 10; // 10분 (1/1000초)
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";

}
