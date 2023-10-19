package com.bcbs.ems.exceptions;

/*
 * Throws exception if employee is not found
 */
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String message){
        super(message);
    }
}
