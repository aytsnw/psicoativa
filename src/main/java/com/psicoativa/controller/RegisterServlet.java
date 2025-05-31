package com.psicoativa.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
        rd.forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if (request.getParameter("type").equals("client")){
            RequestDispatcher rd = request.getRequestDispatcher("/client");
            rd.forward(request, response);
        } else if (request.getParameter("type").equals("psychologist")){
            RequestDispatcher rd = request.getRequestDispatcher("/psychologist");
            rd.forward(request, response);
        } else {
            response.setStatus(400);
        }
    }
}
