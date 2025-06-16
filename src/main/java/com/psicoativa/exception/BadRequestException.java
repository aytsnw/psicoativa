package com.psicoativa.exception;

public class BadRequestException extends RuntimeException{
        public BadRequestException(){
        super("Bad request.");
    }

    public BadRequestException(String message){
        super("Bad request: " + message);
    }
    
}
