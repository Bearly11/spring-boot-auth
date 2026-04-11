package com.springproject.spring_boot_jwt.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String msg){
        super(msg);
    }
}
