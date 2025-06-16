package com.psicoativa.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.util.CpfValidator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="clients")
public class Client extends UserBase{
    @Column(name = "cpf", unique = true)
    private String cpf;

    @OneToMany(mappedBy="client", fetch = FetchType.EAGER)
    private List<Appointment> appointments;

    public void setAppointments(List<Appointment> appointments){
        this.appointments = appointments;
    }
    public List<Appointment> getAppointments(){
        return this.appointments;
    }

    public void setCpf(String cpf) throws InvalidDataException{
        if (cpf.isEmpty()) throw new InvalidDataException("invalid client cpf: empty");
        if (cpf.length() != 11 || !isValidCpf(cpf)) throw new InvalidDataException("invalid client cpf: cpf code is not valid");
        this.cpf = cpf;
    }

    public String getCpf(){
        return this.cpf;
    }

    private boolean isValidCpf(String cpf){
        CpfValidator cpfValidator = new CpfValidator();
        return cpfValidator.validate(cpf);
    }
}
