package com.springproject.spring_boot_jwt.exceptions;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String msg){
        super(msg);
    }
}
