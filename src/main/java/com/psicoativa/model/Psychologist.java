package com.psicoativa.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.psicoativa.exception.InvalidDataException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="psychologists")
public class Psychologist extends UserBase{
    @Column(name = "crp", unique = true)
    private String crp;

    @OneToMany(mappedBy="psychologist", fetch = FetchType.EAGER)
    private List<Appointment> appointments;

    public void setAppointments(List<Appointment> appointments){
        this.appointments = appointments;
    }
    public List<Appointment> getAppointments(){
        return this.appointments;
    }

    public void setCrp(String crp){
        if (crp.isEmpty()) throw new InvalidDataException("Invalid client cpf: empty");
        if (crp.length() != 7 || !isValidCrp(crp)) throw new InvalidDataException("Invalid client cpf: cpf code is not valid");
        this.crp = crp;
    }
    public String getCrp(){
        return this.crp;
    }
    private boolean isValidCrp(String crp){
        //todo
        return true;
    }
}
