package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Map;

import com.psicoativa.dto.AppointmentDto;
import com.psicoativa.exception.BadRequestException;
import com.sun.net.httpserver.HttpServer;

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
            out = response.getWriter();
            AppointmentDto aDto = populateDto(params);
            aService.registerAppointment(aDto);
        } catch (IOException e){
            out.println("Internal Server Error");
            response.setStatus(500);
        }
    }

    private AppointmentDto populateDto(Map<String, String[]> params){
        AppointmentDto aDto = new AppointmentDto();
        try{
            short day = Short.parseShort(params.get("day")[0]);
            short year = Short.parseShort(params.get("year")[0]);
            short month = Short.parseShort(params.get("month")[0]);
        } catch (NumberFormatException e){
            throw new BadRequestException("Bad request: Day, year and month should be of type 'Short'.");
        }

        LocalDate.of(year, month, day);

    }
}