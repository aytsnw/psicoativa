package com.psicoativa.model;

import java.util.List;

import com.psicoativa.exception.InvalidDataException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class UserBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String phone;
    @Column(name="email", unique=true)
    String email;
    @OneToOne(mappedBy="userBase")
    UserAuth userAuth;
    @OneToMany
    List<Appointment> appointments;

    public void setName(String name) throws InvalidDataException{
        if (name.isEmpty()) throw new InvalidDataException("Invalid client name: empty.");
        this.name = name;
    }
    public void setPhone(String phone) throws InvalidDataException{
        if (phone.isEmpty()) throw new InvalidDataException("Invalid client phone: empty.");
        this.phone = phone;
    }
    public void setEmail(String email) throws InvalidDataException{
        if (email.isEmpty()) throw new InvalidDataException("Invalid client email: empty.");
        this.email = email;
    }
    public void setUserAuth(UserAuth userAuth){
        this.userAuth = userAuth;
    }

    public String getName(){
        return this.name;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getEmail(){
        return this.email;
    }
    public UserAuth getUserAuth(){
        return this.userAuth;
    }
    
}
