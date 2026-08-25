package com.sevabank.SevaBank.exception;

public class ResourceNotFoundException extends CustomServiceException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
