package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Map;

import com.psicoativa.dto.AppointmentDto;
import com.psicoativa.exception.BadRequestException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Client;
import com.psicoativa.model.Psychologist;
import com.psicoativa.service.AppointmentService;
import com.psicoativa.service.ClientService;
import com.psicoativa.service.PsychologistService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AppointmentServlet extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        AppointmentService aService = new AppointmentService();
        Map<String, String[]> params = request.getParameterMap();
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}
        try{
            AppointmentDto aDto = populateDto(params);
            aService.saveAppointment(aDto);
            response.setStatus(200);
            out.println("Appointment scheduled!");
        } catch (BadRequestException | ServiceFailedException e){
            out.println(e.getMessage());
            response.setStatus(400);
        }
    }

    private AppointmentDto populateDto(Map<String, String[]> params){
        AppointmentDto aDto = new AppointmentDto();
        short day, year, month, startHour, endHour, startMinute, endMinute;
        int clientId, psychologistId;
        try{
            clientId = Integer.parseInt(params.get("client_id")[0]);
            psychologistId = Integer.parseInt(params.get("psychologist_id")[0]);
            day = Short.parseShort(params.get("day")[0]);
            year = Short.parseShort(params.get("year")[0]);
            month = Short.parseShort(params.get("month")[0]);
            startHour = Short.parseShort(params.get("start_hour")[0]);
            endHour = Short.parseShort(params.get("end_hour")[0]);
            startMinute = Short.parseShort(params.get("start_minute")[0]);
            endMinute = Short.parseShort(params.get("end_minute")[0]);
        } catch (NumberFormatException e){
            throw new BadRequestException("Bad request: day, year, month, start | end hour and start | end minute should be of type short.");
        } catch (NullPointerException e){
            throw new BadRequestException("Bad request: empty parameter.");
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
        ClientService cService = new ClientService();
        return cService.getClient(id);
    }

    private Psychologist findPsychologist(int id){
        PsychologistService pService = new PsychologistService();
        return pService.getPsychologist(id);
    }
}