package com.bcbs.ems.exceptions;

/*
 * Checks if employee exists already
 */
public class EmployeeAlreadyExistsException extends RuntimeException {

    private String message;

    public EmployeeAlreadyExistsException(String message){
        super(message);
    }
}
