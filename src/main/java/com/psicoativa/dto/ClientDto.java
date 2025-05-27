package com.psicoativa.dto;

public class ClientDto extends UserBaseDto{
    private String cpf;

    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public String getCpf(){
        return this.cpf;
    }
    
}
