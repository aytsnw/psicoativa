package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psicoativa.App;
import com.psicoativa.core.UserSession;
import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.BadRequestException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.service.LoginService;
import com.psicoativa.util.UserAuthDtoPopulator;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet{
    private LoginService lService;

    @Override
    public void init() throws ServletException{
        super.init();
        this.lService = (LoginService) getServletContext().getAttribute(App.LOGIN_SERVICE_KEY);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {out = response.getWriter();}
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = request.getSession(false);

        try{
            if (session == null) throw new BadRequestException("client not logged in.");
            UserSession us = new UserSession();
            us.setId((int) session.getAttribute("id"));
            us.setEmail((String) session.getAttribute("email"));
            us.setType((String) session.getAttribute("type"));
            out.print(mapper.writeValueAsString(us));
            response.setStatus(200);
        } catch (JsonProcessingException e){
            System.out.println(e.getMessage());
            response.setStatus(500);
        } catch (BadRequestException e){
            out.print(e.getMessage());
            response.setStatus(400);
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        HttpSession session = request.getSession();

        UserAuthDtoPopulator uDtoPopulator = new UserAuthDtoPopulator();

        try{
            UserAuthDto uDto = uDtoPopulator.populate(request);
            uDto = lService.loginUser(uDto);
            session.setAttribute("id", uDto.getId());
            session.setAttribute("email", uDto.getEmail());
            session.setAttribute("type", uDto.getType());
            out.println("User logged in!");
            response.setStatus(200);
        } catch (ServiceFailedException | BadRequestException e){
            out.println(e.getMessage());
            response.setStatus(400);
        }
    }
}
