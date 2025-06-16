package com.psicoativa.exception;

public class DbOperationFailedException extends RuntimeException{
    public DbOperationFailedException(){
        super("Database operation failed.");
    }

    public DbOperationFailedException(String message){
        super("Database operation failed: " + message);
    }
}
