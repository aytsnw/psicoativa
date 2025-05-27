package com.psicoativa.exception;

public class WrongCredentialsException extends RuntimeException{
    public WrongCredentialsException(){
        super("Wrong credentials.");
    }

    public WrongCredentialsException(String message){
        super(message);
    }
    
}
