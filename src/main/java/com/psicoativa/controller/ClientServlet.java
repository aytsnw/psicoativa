package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.psicoativa.App;
import com.psicoativa.dto.ClientDto;
import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.BadRequestException;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Client;
import com.psicoativa.service.ClientService;
import com.psicoativa.service.RegisterService;
import com.psicoativa.util.ClientDtoPopulator;
import com.psicoativa.util.UserAuthDtoPopulator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ClientServlet extends HttpServlet{
    private ClientService cService;
    private RegisterService rService;

    @Override
    public void init() throws ServletException{
        super.init();
        this.cService = (ClientService) getServletContext().getAttribute(App.CLIENT_SERVICE_KEY);
        this.rService = (RegisterService) getServletContext().getAttribute(App.REGISTER_SERVICE_KEY);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objMapper.registerModule(new JavaTimeModule());

        ClientDto cDto = null;
        List<ClientDto> cDtos= null;

        String parameter = request.getParameter("parameter");

        try {
            if (parameter == null) {
                throw new BadRequestException("'parameter' is null");
            } else if (parameter.equals("id")) {
                parameter = request.getParameter("client_id");
                cDto = cService.getClientDto(Integer.parseInt(parameter));
                out.print(objMapper.writeValueAsString(cDto));
            } else if (parameter.equals("name")) {
                parameter = request.getParameter("name");
                cDtos = cService.getClientDto(parameter);
                out.print(objMapper.writeValueAsString(cDtos));
            }
            response.setStatus(200);
        } catch (NumberFormatException e){
            out.print("id must be of type integer and not empty.");
            response.setStatus(400);
        } catch (ServiceFailedException e) {
            out.print(e.getMessage());
            response.setStatus(400);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        ClientDtoPopulator cDtoPopulator = new ClientDtoPopulator();
        UserAuthDtoPopulator uDtoPopulator = new UserAuthDtoPopulator();

        try {
            ClientDto cDto = cDtoPopulator.populate(request);
            UserAuthDto uDto = uDtoPopulator.populate(request);
            rService.registerClient(uDto, cDto);
            response.setStatus(200);
            out.println("Client registered!");
        } catch (ServiceFailedException | InvalidDataException | BadRequestException e) {
            response.setStatus(400);
            out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response){}

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response){}
}
