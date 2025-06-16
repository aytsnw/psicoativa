package com.psicoativa.model;

import org.mindrot.jbcrypt.BCrypt;

import com.psicoativa.exception.InvalidDataException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="users")
public class UserAuth {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(unique=true)
    private String email;
    @Transient
    private String password;
    private String passwordHash;
    private String type; 
    @OneToOne
    private UserBase userBase;

    public void setId(int id){
        this.id = id;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        if (password.isEmpty()) throw new InvalidDataException("invalid client password: empty.");
        else if (password.length() < 6) throw new InvalidDataException("invalid client password: too short");
        this.password = password;
        setPasswordHash();
    }

    private void setPasswordHash(){
        this.passwordHash = BCrypt.hashpw(this.password, BCrypt.gensalt());
    }

    public void setType(String type){
        this.type = type;
    }

    public void setUserBase(UserBase userBase){
        this.userBase = userBase;
    }

    public int getId(){
        return this.id;
    }

    public String getEmail(){
        return this.email;
    }
    public String getPasswordHash(){
        return this.passwordHash;
    }
    public String getPassword(){
        return this.password;
    }
    public String getType(){
        return this.type;
    }
    public UserBase getUserBase(){
        return this.userBase;
    }
}
