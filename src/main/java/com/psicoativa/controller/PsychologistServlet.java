package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.psicoativa.App;
import com.psicoativa.dto.PsychologistDto;
import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.BadRequestException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.service.PsychologistService;
import com.psicoativa.service.RegisterService;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response){}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        try {
            PsychologistDto pDto = populatePsychologistDto(request);
            UserAuthDto uDto = populateUserAuthDto(request);
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

    private PsychologistDto populatePsychologistDto(HttpServletRequest request) throws BadRequestException{
        PsychologistDto pDto = new PsychologistDto();
        String name = request.getParameter("name");
        String crp = request.getParameter("crp");
        String phone = request.getParameter("phone");
        String email =request.getParameter("email");
        if (name.isEmpty()) throw new BadRequestException("Bad request: 'name' is empty");
        if (crp.isEmpty()) throw new BadRequestException("Bad request: 'crp' is empty");
        if (phone.isEmpty()) throw new BadRequestException("Bad request: 'phone' is empty");
        if (email.isEmpty()) throw new BadRequestException("Bad request: 'email' is empty");
        pDto.setName(name);
        pDto.setCrp(crp);
        pDto.setPhone(phone);
        pDto.setEmail(email);
        return pDto;
    }

    private UserAuthDto populateUserAuthDto(HttpServletRequest request) throws BadRequestException {
        UserAuthDto uDto = new UserAuthDto();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String type = request.getParameter("type");
        if (email.isEmpty()) throw new BadRequestException("Bad request: 'email' is empty");
        if (password.isEmpty()) throw new BadRequestException("Bad request: 'crp' is empty");
        if (type.isEmpty()) throw new BadRequestException("Bad request: 'type' is empty");
        uDto.setEmail(email);
        uDto.setPassword(password);
        uDto.setType(type);
        return uDto;
    }
}
