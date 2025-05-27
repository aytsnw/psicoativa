package com.psicoativa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="psychologists")
public class Psychologist extends UserBase{
    @Column(name = "crp", unique = true)
    private String crp;

    public void setCrp(String crp){
        this.crp = crp;
    }
    public String getCrp(){
        return this.crp;
    }
}
