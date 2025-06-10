package com.psicoativa.dto;

import java.time.LocalDate;

import com.psicoativa.model.Client;
import com.psicoativa.model.Psychologist;

public class AppointmentDto {
    private int id;
    private Client client;
    private Psychologist psychologist;
    
    private LocalDate date;
    private short startHour;
    private short startMinute;
    private short endHour;
    private short endminute;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Psychologist getPsychologist() {
        return psychologist;
    }
    public void setPsychologist(Psychologist psychologist) {
        this.psychologist = psychologist;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public short getStartHour() {
        return startHour;
    }
    public void setStartHour(short startHour) {
        this.startHour = startHour;
    }
    public short getStartMinute() {
        return startMinute;
    }
    public void setStartMinute(short startMinute) {
        this.startMinute = startMinute;
    }
    public short getEndHour() {
        return endHour;
    }
    public void setEndHour(short endHour) {
        this.endHour = endHour;
    }
    public short getEndminute() {
        return endminute;
    }
    public void setEndminute(short endminute) {
        this.endminute = endminute;
    }
}
