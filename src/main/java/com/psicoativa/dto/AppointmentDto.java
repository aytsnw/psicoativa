package com.psicoativa.dto;

import java.time.LocalDate;

import com.psicoativa.model.Client;
import com.psicoativa.model.Psychologist;
import com.psicoativa.core.Dto;

public class AppointmentDto extends Dto {
    private int id;
    private int clientId;
    private int psychologistId;
    
    private LocalDate date;
    private short startHour;
    private short startMinute;
    private short endHour;
    private short endMinute;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getClientId() {return clientId;}
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public int getPsychologistId() {
        return this.psychologistId;
    }
    public void setPsychologistId(int psychologistId) {
        this.psychologistId = psychologistId;
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
    public short getEndMinute() {
        return endMinute;
    }
    public void setEndMinute(short endMinute) {
        this.endMinute = endMinute;
    }
}
