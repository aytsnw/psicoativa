package com.psicoativa.service;

import com.psicoativa.dto.AppointmentDto;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Appointment;
import com.psicoativa.model.Client;
import com.psicoativa.model.Psychologist;
import com.psicoativa.repository.AppointmentRepository;
import com.psicoativa.util.AppointmentDtoPopulator;

public class AppointmentService {
    private final AppointmentRepository aRepo;
    private final ClientService cService;
    private final PsychologistService pService;

    public AppointmentService(AppointmentRepository aRepo, ClientService cService, PsychologistService pService){
        this.aRepo = aRepo;
        this.cService = cService;
        this.pService = pService;
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

    public AppointmentDto getAppointmentDto(int id) throws ServiceFailedException{
        AppointmentDtoPopulator aDtoPopulator = new AppointmentDtoPopulator();
        Appointment ap = aRepo.findById(id);
        if (ap == null) throw new ServiceFailedException("Service failure: appointment not found with id: " + id);
        return aDtoPopulator.populate(ap);
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
        Client client = cService.getClient(aDto.getClientId());
        Psychologist psychologist = pService.getPsychologist(aDto.getPsychologistId());
        ap.setClient(client);
        ap.setPsychologist(psychologist);
        ap.setDate(aDto.getDate());
        ap.setStartHour(aDto.getStartHour());
        ap.setEndHour(aDto.getEndHour());
        ap.setStartMinute(aDto.getStartMinute());
        ap.setEndMinute(aDto.getEndMinute());
        ap.setStartTimeId();
        ap.setEndTimeId();
        int duration = ap.getDurationMinutes();
        if (duration > Appointment.MAX_DURATION_MINUTES || duration < Appointment.MIN_DURATION_MINUTES){
            throw new InvalidDataException("Invalid data: appointment length exceeds time limit (59 min) or is less than min (29 min).");
        }
        return ap;
    }
}
