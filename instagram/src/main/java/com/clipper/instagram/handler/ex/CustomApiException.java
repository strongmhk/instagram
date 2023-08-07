package com.clipper.instagram.handler.ex;

public class CustomApiException extends RuntimeException{

    // 객체를 구분할 때!
    private static final long serialVersonUID = 1L;


    public CustomApiException(String message){
        super(message); // 부모한테 message 던지기
    }




}
