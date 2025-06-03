package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psicoativa.App;
import com.psicoativa.dto.ClientDto;
import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.BadRequestException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Client;
import com.psicoativa.service.ClientService;
import com.psicoativa.service.RegisterService;

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

        int id;
        try {
            id = Integer.parseInt(request.getParameter("client_id"));
            Client client = cService.getClient(id);
            String json = objMapper.writeValueAsString(client);
            out.print(json);
            response.setStatus(200);
        } catch (NumberFormatException e){
            out.println("Bad request: id must be of type integer and not empty.");
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

        try {
            ClientDto cDto = populateClientDto(request);
            UserAuthDto uDto = populateUserAuthDto(request);
            rService.registerClient(uDto, cDto);
            response.setStatus(200);
            out.println("Client registered!");
        } catch (ServiceFailedException | BadRequestException e) {
            response.setStatus(400);
            out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response){}

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response){}


    private ClientDto populateClientDto(HttpServletRequest request) throws BadRequestException{
        ClientDto cDto = new ClientDto();
        String name = request.getParameter("name");
        String cpf = request.getParameter("cpf");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        if (name.isEmpty()) throw new BadRequestException("Empty parameter: name");
        if (cpf.isEmpty()) throw new BadRequestException("Empty parameter: cpf");
        if (phone.isEmpty()) throw new BadRequestException("Empty parameter: phone");
        if (email.isEmpty()) throw new BadRequestException("Empty parameter: email");
        cDto.setName(name);
        cDto.setCpf(cpf);
        cDto.setPhone(phone);
        cDto.setEmail(email);
        return cDto;
    }

    private UserAuthDto populateUserAuthDto(HttpServletRequest request) throws BadRequestException{
        UserAuthDto uDto = new UserAuthDto();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String type = request.getParameter("type");
        if (email.isEmpty()) throw new BadRequestException("Empty parameter: email");
        if (password.isEmpty()) throw new BadRequestException("Empty parameter: password");
        if (type.isEmpty()) throw new BadRequestException("Empty parameter: type");
        uDto.setEmail(email);
        uDto.setPassword(password);
        uDto.setType(type);
        return uDto;
    }
}
