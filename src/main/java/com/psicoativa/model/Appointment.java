package com.psicoativa.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserBase client;
    @ManyToOne
    @JoinColumn(name = "psychologist_id")
    private UserBase psychologist;
    
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
    public UserBase getClient() {
        return client;
    }
    public void setClient(UserBase client) {
        this.client = client;
    }
    public UserBase getPsychologist() {
        return psychologist;
    }
    public void setPsychologist(UserBase psychologist) {
        this.psychologist = psychologist;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Short getStartHour() {
        return startHour;
    }
    public void setStartHour(short startHour) {
        this.startHour = startHour;
    }
    public Short getStartMinute() {
        return startMinute;
    }
    public void setStartMinute(short startMinute) {
        this.startMinute = startMinute;
    }
    public Short getEndHour() {
        return endHour;
    }
    public void setEndHour(short endHour) {
        this.endHour = endHour;
    }
    public Short getEndMinute() {
        return endMinute;
    }
    public void setEndminute(short endMinute) {
        this.endMinute = endMinute;
    }

    
}
