package com.psicoativa.util;

import com.psicoativa.dto.ClientDto;
import com.psicoativa.exception.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;

public class ClientDtoPopulator implements DtoPopulator<ClientDto>{
    @Override
    public ClientDto populate(HttpServletRequest request){
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

    
}
