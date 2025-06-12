package com.psicoativa.dto;

import com.psicoativa.core.Dto;

import java.util.List;

public class UserBaseDto extends Dto {
    private int id;
    private String name;
    private String phone;
    private String email;
    private List<Integer> appointments;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public void setName(String name){
        this.name = name;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setEmail(String email){
        this.email = email;
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
    public List<Integer> getAppointments() {return appointments;}
    public void setAppointments(List<Integer> appointments) {this.appointments = appointments;}
}
