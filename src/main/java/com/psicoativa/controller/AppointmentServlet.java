package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.psicoativa.App;
import com.psicoativa.dto.AppointmentDto;
import com.psicoativa.exception.BadRequestException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.service.AppointmentService;
import com.psicoativa.util.AppointmentDtoPopulator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AppointmentServlet extends HttpServlet{
    private AppointmentService aService;

    @Override
    public void init() throws ServletException{
        super.init();
        this.aService = (AppointmentService) getServletContext().getAttribute(App.APPOINTMENT_SERVICE_KEY);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.registerModule(new JavaTimeModule());

        try {
            String appointmentIdString = request.getParameter("appointment_id");
            if (appointmentIdString == null || appointmentIdString.isEmpty()) throw new BadRequestException("Bad request: 'appointment_id' is empty or null");
            Integer appointmentId = parseId(appointmentIdString);
            AppointmentDto aDto = aService.getAppointmentDto(appointmentId);
            out.print(objMapper.writeValueAsString(aDto));
            response.setHeader("Content-Type","application/json");
            response.setStatus(200);
        } catch (BadRequestException | ServiceFailedException e) {
            out.print("Bad request: " + e.getMessage());
            response.setStatus(400);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        try{
            String operation = request.getParameter("operation");
            String appointmentIdString = request.getParameter("appointment_id");
            if (operation == null || operation.isEmpty()) throw new BadRequestException("Bad request: 'operation' is empty or null");
            if (appointmentIdString == null || appointmentIdString.isEmpty()) throw new BadRequestException("Bad request: 'appointment_id' is empty or null");
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

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        AppointmentDtoPopulator aDtoPopulator = new AppointmentDtoPopulator();
        PrintWriter out = null;
        try {out = response.getWriter();}
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}
        
        try{
            AppointmentDto aDto = aDtoPopulator.populate(request);
            aService.saveAppointment(aDto);
            response.setStatus(200);
            out.println("Appointment scheduled!");
        } catch (BadRequestException | ServiceFailedException e){
            out.println(e.getMessage());
            response.setStatus(400);
        }
    }

    private Integer parseId(String idString){
        try{return Integer.parseInt(idString);} catch (NumberFormatException e){
            throw new BadRequestException("Bad request: id must be of type 'int'");
        }
    }
}