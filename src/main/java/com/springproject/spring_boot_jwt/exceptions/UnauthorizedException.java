package com.springproject.spring_boot_jwt.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String msg){
        super(msg);
    }
}
