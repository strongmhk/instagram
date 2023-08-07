package com.clipper.instagram.handler.ex;

public class CustomException extends RuntimeException{

    // 객체를 구분할 때!
    private static final long serialVersonUID = 1L;

    public CustomException(String message){
        super(message); // 부모한테 message 던지기
    }




}
