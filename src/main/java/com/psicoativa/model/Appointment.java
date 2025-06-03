package com.psicoativa.model;

import java.time.LocalDate;

import com.psicoativa.exception.InvalidDataException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Transient
    public final short MAX_DURATION_MINUTES = 59;
    @Transient
    public final short MIN_DURATION_MINUTES = 29;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserBase client;
    @ManyToOne
    @JoinColumn(name = "psychologist_id")
    private UserBase psychologist;
    private String status = "active"; 
    
    private LocalDate date;
    private short startHour;
    private short startMinute;
    private short endHour;
    private short endMinute;

    private int startTimeId;
    private int endTimeId;

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
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(String status){
        return this.status;
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
        if (startHour > 23 ||startHour < 0) throw new InvalidDataException("Hour value must be less than or equal to 23 and positive.");
        this.startHour = startHour;
    }
    public Short getStartMinute() {
        return startMinute;
    }
    public void setStartMinute(short startMinute) {
        if (startMinute > 59 || startMinute < 0) throw new InvalidDataException("Minute value must be less than or equal to 59 and positive.");
        this.startMinute = startMinute;
    }
    public Short getEndHour() {
        return endHour;
    }
    public void setEndHour(short endHour) {
        if (endHour > 23 ||endHour < 0) throw new InvalidDataException("Hour value must be less than or equal to 23 and positive.");
        this.endHour = endHour;
    }
    public Short getEndMinute() {
        return endMinute;
    } 
    public void setEndminute(short endMinute) {
        this.endMinute = endMinute;
    }
    public int getStartTimeId() {
        return startTimeId;
    }
    public void setStartTimeId(){
        this.startTimeId = calculateTimeId(this.startHour, this.startMinute);
    }
    public int getEndTimeId() {
        return endTimeId;
    }
    public void setEndTimeId() {
        this.endTimeId = calculateTimeId(this.endHour, this.endMinute);
    }
    private int calculateTimeId(short hour, short minute){
        return hour * 60 + minute;
    }
    
    public int getDurationMinutes(){
        return this.endTimeId - this.startTimeId;
    }
}
