package com.psicoativa.service;

import com.psicoativa.dto.AppointmentDto;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Appointment;
import com.psicoativa.repository.AppointmentRepository;

public class AppointmentService {
    private final AppointmentRepository aRepo;

    public AppointmentService(AppointmentRepository aRepo){
        this.aRepo = aRepo;
    }

    public void saveAppointment(AppointmentDto aDto){
        try {
            Appointment ap = parseDto(aDto);
            if (!isTimeSlotFree(ap, aRepo)){
                throw new ServiceFailedException("Time slot already booked.");
            }
            aRepo.addToDb(ap);
        } catch (InvalidDataException | DbOperationFailedException e) {
            throw new ServiceFailedException(e.getMessage());
        }
    }

    private boolean isTimeSlotFree(Appointment ap, AppointmentRepository aRepo){
        Integer startTimeCandidate = Integer.valueOf(ap.getStartHour().toString() + ap.getStartMinute().toString());
        Integer endTimeCandidate = Integer.valueOf(ap.getEndHour().toString() + ap.getEndMinute().toString());
        Appointment apCompare = aRepo.findByEndHour(ap.getDate(), ap.getStartHour());
        if (apCompare == null){return true;}
        Integer startTimeCompare = Integer.valueOf(apCompare.getStartHour().toString() + apCompare.getStartMinute().toString());
        Integer endTimeCompare= Integer.valueOf(apCompare.getEndHour().toString() + apCompare.getEndMinute().toString());
        return !(startTimeCandidate <= endTimeCompare && endTimeCandidate >= startTimeCompare);
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
