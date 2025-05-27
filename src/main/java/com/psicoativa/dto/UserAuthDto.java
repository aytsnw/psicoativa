package com.psicoativa.dto;

public class UserAuthDto {
    int id;
    String email;
    String password;
    String passwordHash;
    private String type;

    public void setId(int id){
        this.id = id;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }
    public void setType(String type){
        this.type = type;
    }

    public int getId(){
        return this.id;
    }
    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }
    public String getPasswordHash(){
        return this.passwordHash;
    }
    public String getType(){
        return this.type;
    }
    
    
}
