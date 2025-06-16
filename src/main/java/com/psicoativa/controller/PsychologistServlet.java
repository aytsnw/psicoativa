package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.psicoativa.App;
import com.psicoativa.dto.PsychologistDto;
import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.BadRequestException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Psychologist;
import com.psicoativa.service.PsychologistService;
import com.psicoativa.service.RegisterService;
import com.psicoativa.util.PsychologistDtoPopulator;
import com.psicoativa.util.UserAuthDtoPopulator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PsychologistServlet extends HttpServlet{
    private PsychologistService pService;
    private RegisterService rService;

    @Override
    public void init() throws ServletException{
        super.init();
        this.pService = (PsychologistService) getServletContext().getAttribute(App.PSYCHOLOGIST_SERVICE_KEY);
        this.rService = (RegisterService) getServletContext().getAttribute(App.REGISTER_SERVICE_KEY);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.registerModule(new JavaTimeModule());

        int id;
        try {
            id = Integer.parseInt(request.getParameter("psychologist_id"));
            PsychologistDto pDto = pService.getPsychologistDto(id);
            out.print(objMapper.writeValueAsString(pDto));
            response.setStatus(200);
        } catch (NumberFormatException e){
            out.println("id must be of type integer and not empty.");
            response.setStatus(400);
        } catch (ServiceFailedException e) {
            out.println(e.getMessage());
            response.setStatus(400);
        }catch (JsonProcessingException e){
            out.print(e.getMessage());
            response.setStatus(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        PsychologistDtoPopulator pDtoPopulator = new PsychologistDtoPopulator();
        UserAuthDtoPopulator uDtoPopulator = new UserAuthDtoPopulator();

        try {
            PsychologistDto pDto = pDtoPopulator.populate(request);
            UserAuthDto uDto = uDtoPopulator.populate(request);
            rService.registerPsychologist(uDto, pDto);
            response.setStatus(200);
            out.println("Psychologist registered!");
        } catch (ServiceFailedException | BadRequestException e) {
            response.setStatus(400);
            out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response){}

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response){}

}
