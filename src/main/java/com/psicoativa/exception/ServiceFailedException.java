package com.psicoativa.exception;

public class ServiceFailedException extends RuntimeException{
    public ServiceFailedException(){
        super("Service failure.");
    }

    public ServiceFailedException(String message){
        super(message);
    }
    
}