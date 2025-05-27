package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.psicoativa.dto.ClientDto;
import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.service.RegisterService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ClientServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        RegisterService rService = new RegisterService();
        Map<String, String[]> params = request.getParameterMap();
        ClientDto cDto = populateClientDto(params);
        UserAuthDto uDto = populateUserAuthDto(params);
        try {
            rService.registerClient(uDto, cDto);
            response.setStatus(200);
            out.println("Client registered!");
        } catch (ServiceFailedException e) {
            response.setStatus(400);
            out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response){

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response){

    }

    private ClientDto populateClientDto(Map<String, String[]> params){
        ClientDto cDto = new ClientDto();
        cDto.setName(params.get("name")[0]);
        cDto.setCpf((params.get("cpf")[0]));
        cDto.setPhone(params.get("phone")[0]);
        cDto.setEmail(params.get("email")[0]);
        return cDto;
    }

    private UserAuthDto populateUserAuthDto(Map<String, String[]> params){
        UserAuthDto uDto = new UserAuthDto();
        uDto.setEmail(params.get("email")[0]);
        uDto.setPassword(params.get("password")[0]);
        uDto.setType(params.get("type")[0]);
        return uDto;
    }
    
}
