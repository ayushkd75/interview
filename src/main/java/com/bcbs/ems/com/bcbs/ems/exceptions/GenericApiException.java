package com.bcbs.ems.exceptions;


public class GenericApiException extends RuntimeException {

    private String message;

    public GenericApiException(String message){
        super(message);
    }
}
