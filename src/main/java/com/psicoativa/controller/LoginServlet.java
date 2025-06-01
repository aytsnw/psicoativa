package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.psicoativa.App;
import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.service.LoginService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;

public class LoginServlet extends HttpServlet{
    private LoginService lService;

    @Override
    public void init() throws ServletException{
        super.init();
        this.lService = (LoginService) getServletContext().getAttribute(App.LOGIN_SERVICE_KEY);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        PrintWriter out = null;
        try {out = response.getWriter();} 
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        Map<String, String[]> params = request.getParameterMap();

        UserAuthDto uDto = new UserAuthDto();
        uDto.setEmail(params.get("email")[0]);
        uDto.setPassword(params.get("password")[0]);
        try{
            out = response.getWriter();
            uDto = lService.loginUser(uDto);
            session.setAttribute("id", uDto.getId());
            session.setAttribute("email", uDto.getEmail());
            session.setAttribute("type", uDto.getType());
            response.setStatus(200);
            out.println("User logged in!");
        } catch (ServiceFailedException e){
            out.println(e.getMessage());
            response.setStatus(400);
        } catch (IOException e){
            out.println("Internal Server Error");
            response.setStatus(500);
        }
    }
}
