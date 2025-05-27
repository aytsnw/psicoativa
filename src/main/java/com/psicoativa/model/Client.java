package com.psicoativa.model;

import com.psicoativa.exception.InvalidDataException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="clients")
public class Client extends UserBase{
    @Column(name = "cpf", unique = true)
    private String cpf;

    public void setCpf(String cpf) throws InvalidDataException{
        if (name.isEmpty()) throw new InvalidDataException("Invalid client cpf: empty");
        this.cpf = cpf;
    }
    public String getCpf(){
        return this.cpf;
    }
}
