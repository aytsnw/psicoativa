package com.psicoativa.util;

import com.psicoativa.core.Dto;
import com.psicoativa.dto.ClientDto;
import com.psicoativa.exception.BadRequestException;

import com.psicoativa.model.Appointment;
import com.psicoativa.model.Client;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public class ClientDtoPopulator implements DtoPopulator<Client>{
    private final ClientDto cDto;

    public ClientDtoPopulator(){
        this.cDto = new ClientDto();
    }
    @Override
    public ClientDto populate(HttpServletRequest request){
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

    @Override
    public ClientDto populate(Client model) {
        cDto.setId(model.getId());
        cDto.setName(model.getName());
        cDto.setCpf(model.getCpf());
        cDto.setPhone(model.getPhone());
        cDto.setEmail(model.getEmail());
        List<Integer> appointments = new ArrayList<>();
        for (Appointment a : model.getAppointments()){
            appointments.add(a.getId());
        }
        cDto.setAppointments(appointments);
        return cDto;
    }
}
