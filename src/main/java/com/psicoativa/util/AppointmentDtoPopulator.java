package com.psicoativa.util;

import java.time.LocalDate;

import com.psicoativa.core.Dto;
import com.psicoativa.core.Model;
import com.psicoativa.dto.AppointmentDto;
import com.psicoativa.exception.BadRequestException;
import com.psicoativa.model.Appointment;
import com.psicoativa.model.Client;
import com.psicoativa.model.Psychologist;
import com.psicoativa.service.ClientService;
import com.psicoativa.service.PsychologistService;

import jakarta.servlet.http.HttpServletRequest;

public class AppointmentDtoPopulator implements DtoPopulator<Appointment>{
    private final AppointmentDto aDto;

    public AppointmentDtoPopulator(){
        this.aDto = new AppointmentDto();
    }

    @Override
    public AppointmentDto populate(HttpServletRequest request) throws BadRequestException {
        short day, year, month, startHour, endHour, startMinute, endMinute;
        int clientId, psychologistId;
        try{
            clientId = Integer.parseInt(request.getParameter("client_id"));
            psychologistId = Integer.parseInt(request.getParameter("psychologist_id"));
            day = Short.parseShort(request.getParameter("day"));
            year = Short.parseShort(request.getParameter("year"));
            month = Short.parseShort(request.getParameter("month"));
            startHour = Short.parseShort(request.getParameter("start_hour"));
            endHour = Short.parseShort(request.getParameter("end_hour"));
            startMinute = Short.parseShort(request.getParameter("start_minute"));
            endMinute = Short.parseShort(request.getParameter("end_minute"));
        } catch (NumberFormatException e){
            throw new BadRequestException("Bad request: day, year, month, start | end hour and start | end minute should be of type short.");
        }

        aDto.setClientId(clientId);
        aDto.setPsychologistId(psychologistId);
        aDto.setDate(LocalDate.of(year, month, day));
        aDto.setStartHour(startHour);
        aDto.setEndHour(endHour);
        aDto.setStartMinute(startMinute);
        aDto.setEndMinute(endMinute);
        return aDto;
    }

    @Override
    public AppointmentDto populate(Appointment model){
        int clientId, psychologistId;
        Client client = model.getClient();
        Psychologist psy = model.getPsychologist();
        clientId = client.getId();
        psychologistId = psy.getId();
        aDto.setId(model.getId());
        aDto.setClientId(clientId);
        aDto.setPsychologistId(psychologistId);
        aDto.setDate(model.getDate());
        aDto.setStartHour(model.getStartHour());
        aDto.setEndHour(model.getEndHour());
        aDto.setStartMinute(model.getStartMinute());
        aDto.setEndMinute(model.getEndMinute());
        return aDto;
    }
    
}
