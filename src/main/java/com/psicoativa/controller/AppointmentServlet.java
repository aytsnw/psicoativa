package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.psicoativa.App;
import com.psicoativa.dto.AppointmentDto;
import com.psicoativa.exception.BadRequestException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.service.AppointmentService;
import com.psicoativa.service.ClientService;
import com.psicoativa.service.PsychologistService;
import com.psicoativa.util.AppointmentDtoPopulator;

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

    private Integer parseId(String idString){
        try{return Integer.parseInt(idString);} catch (NumberFormatException e){
            throw new BadRequestException("Bad request: id must be of type 'int'");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        AppointmentDtoPopulator aDtoPopulator = new AppointmentDtoPopulator(cService, pService);
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
}