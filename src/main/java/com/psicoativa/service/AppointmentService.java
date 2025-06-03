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
        int start = ap.getStartTimeId();
        int end = ap.getEndTimeId();
        return aRepo.findByRange(ap.getDate(), start, end).isEmpty();
    }

    public void cancelAppointment(int appointmentId){
        try{aRepo.makeCanceled(appointmentId);} catch(DbOperationFailedException e){
            throw new ServiceFailedException("Service failure: Couldn't cancel appointment: " + e.getMessage());
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
        ap.setStartTimeId();
        ap.setEndTimeId();
        int duration = ap.getDurationMinutes();
        if (duration > ap.MAX_DURATION_MINUTES || duration < ap.MIN_DURATION_MINUTES){
            throw new InvalidDataException("Invalid data: appointment length exceeds time limit (59 min) or is less than min (29 min).");
        }
        return ap;
    }
}
