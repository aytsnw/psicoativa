package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import com.psicoativa.App;
import com.psicoativa.dto.AppointmentDto;
import com.psicoativa.exception.BadRequestException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Client;
import com.psicoativa.model.Psychologist;
import com.psicoativa.service.AppointmentService;
import com.psicoativa.service.ClientService;
import com.psicoativa.service.PsychologistService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AppointmentServlet extends HttpServlet{
    private AppointmentService aService;
    private ClientService cService;
    private PsychologistService pService;

    @Override
    public void init() throws ServletException{
        super.init();
        this.aService = (AppointmentService) getServletContext().getAttribute(App.APPOINTMENT_SERVICE_KEY);
        this.cService = (ClientService) getServletContext().getAttribute(App.CLIENT_SERVICE_KEY);
        this.pService = (PsychologistService) getServletContext().getAttribute(App.PSYCHOLOGIST_SERVICE_KEY);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        try{
            String operation = request.getParameter("operation");
            String appointmentIdString = request.getParameter("appointment_id");
            if (operation == null | operation.isEmpty()) throw new BadRequestException("Bad request: 'operation' is empty or null");
            if (appointmentIdString == null | appointmentIdString.isEmpty()) throw new BadRequestException("Bad request: 'appointment_id' is empty or null");
            Integer appointmentId = parseId(appointmentIdString);

            if (operation.equals("cancel")){
                aService.cancelAppointment(appointmentId);
                response.setStatus(200);
                out.print("Appointment canceled.");
            }
            else throw new BadRequestException("Bad Request: options are 'cancel'");
        } catch (ServiceFailedException | BadRequestException e){
            response.setStatus(400);
            out.print(e.getMessage());
        }
    }

    private Integer parseId(String idString){
        try{return Integer.parseInt(idString);} catch (NumberFormatException e){
            throw new BadRequestException("Bad request: id must be of type 'int'");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}
        try{
            AppointmentDto aDto = populateAppointmentDto(request);
            aService.saveAppointment(aDto);
            response.setStatus(200);
            out.println("Appointment scheduled!");
        } catch (BadRequestException | ServiceFailedException e){
            out.println(e.getMessage());
            response.setStatus(400);
        }
    }

    private AppointmentDto populateAppointmentDto(HttpServletRequest request) throws BadRequestException {
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