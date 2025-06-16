package com.psicoativa.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.psicoativa.exception.BadRequestException;
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
        PrintWriter out = null;
        try {out = response.getWriter();}
        catch (IOException e) {e.printStackTrace();response.setStatus(500);}

        String type = request.getParameter("type");

        try {
            if (type == null) throw new BadRequestException("'type' null.");
            if (request.getParameter("type").equals("client")){
                RequestDispatcher rd = request.getRequestDispatcher("/client");
                rd.forward(request, response);
            } else if (request.getParameter("type").equals("psychologist")){
                RequestDispatcher rd = request.getRequestDispatcher("/psychologist");
                rd.forward(request, response);
            } else {
                throw new BadRequestException("invalid type. Availabe: 'client' | 'psychologist'");
            }
        } catch (BadRequestException e){
            response.setStatus(400);
            out.print(e.getMessage());
        }
    }
}
