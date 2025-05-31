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
        if (cpf.isEmpty()) throw new InvalidDataException("Invalid client cpf: empty");
        if (cpf.length() != 11 || !isValidCpf(cpf)) throw new InvalidDataException("Invalid client cpf: cpf code is not valid");
        this.cpf = cpf;
    }

    public String getCpf(){
        return this.cpf;
    }

    private boolean isValidCpf(String cpf){
        //todo
        return true;
    }
}
