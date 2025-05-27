package com.psicoativa.dto;

public class PsychologistDto extends UserBaseDto{
    private String crp;

    public void setCrp(String crp){
        this.crp = crp;
    }
    public String getCrp(){
        return this.crp;
    }
    
}
