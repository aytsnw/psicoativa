package com.psicoativa.exception;

public class InvalidDataException extends RuntimeException{
    public InvalidDataException(){
        super("Invalid data.");
    }

    public InvalidDataException(String message){
        super("Invalid data: " + message);
    }
}
