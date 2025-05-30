package com.psicoativa.service;

import com.psicoativa.model.Appointment;
import com.psicoativa.repository.AppointmentRepository;
import com.psicoativa.dto.AppointmentDto;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.exception.DbOperationFailedException;

public class AppointmentService {
    public void registerAppointment(AppointmentDto aDto){
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
        return ap;
    }
    
}
