package com.psicoativa.service;

import java.time.LocalDate;

import com.psicoativa.dto.AppointmentDto;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Appointment;
import com.psicoativa.repository.AppointmentRepository;

public class AppointmentService {
    public void saveAppointment(AppointmentDto aDto){
        AppointmentRepository aRepo = new AppointmentRepository();
        try {
            Appointment ap = parseDto(aDto);
            aRepo.addToDb(ap);
        } catch (InvalidDataException | DbOperationFailedException e) {
            throw new ServiceFailedException(e.getMessage());
        }
    }

    private Appointment parseDto(AppointmentDto aDto) throws InvalidDataException{
        Appointment ap = new Appointment();
        ap.setClient(aDto.getClient());
        ap.setPsychologist(aDto.getPsychologist());
        ap.setDate(aDto.getDate());
        ap.setStartHour(aDto.getStartHour());
        ap.setEndHour(aDto.getEndHour());
        ap.setStartMinute(aDto.getStartMinute());
        ap.setEndminute(aDto.getEndminute());
        return ap;
    }
    
}
