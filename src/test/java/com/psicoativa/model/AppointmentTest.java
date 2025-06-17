package com.psicoativa.model;

import com.psicoativa.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {

    @Test
    void setInvalidStartHour(){
        Appointment ap = new Appointment();
        short hour = 60;
        assertThrowsExactly(InvalidDataException.class, () -> ap.setStartHour(hour));
    }

    @Test
    void setValidStartHour(){
        Appointment ap = new Appointment();
        short hour = 38;
        assertDoesNotThrow(() -> ap.setStartHour(hour));
    }
}
