package com.psicoativa.util;

import java.time.LocalDate;

import com.psicoativa.dto.AppointmentDto;
import com.psicoativa.exception.BadRequestException;
import com.psicoativa.model.Client;
import com.psicoativa.model.Psychologist;
import com.psicoativa.service.ClientService;
import com.psicoativa.service.PsychologistService;

import jakarta.servlet.http.HttpServletRequest;

public class AppointmentDtoPopulator implements DtoPopulator<AppointmentDto>{
    private final ClientService cService;
    private final PsychologistService pService;

    public AppointmentDtoPopulator(ClientService cService, PsychologistService pService){
        this.cService = cService;
        this.pService = pService;
    }

    @Override
    public AppointmentDto populate(HttpServletRequest request) throws BadRequestException {
        AppointmentDto aDto = new AppointmentDto();
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

        aDto.setClient(findClient(clientId));
        aDto.setPsychologist(findPsychologist(psychologistId));
        aDto.setDate(LocalDate.of(year, month, day));
        aDto.setStartHour(startHour);
        aDto.setEndHour(endHour);
        aDto.setStartMinute(startMinute);
        aDto.setEndminute(endMinute);
        return aDto;
    }

    private Client findClient(int id){
        return cService.getClient(id);
    }

    private Psychologist findPsychologist(int id){
        return pService.getPsychologist(id);
    }
    
}
