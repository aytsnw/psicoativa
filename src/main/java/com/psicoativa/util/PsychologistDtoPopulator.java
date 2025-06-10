package com.psicoativa.util;

import com.psicoativa.dto.PsychologistDto;
import com.psicoativa.exception.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;

public class PsychologistDtoPopulator implements DtoPopulator<PsychologistDto>{
    @Override
    public PsychologistDto populate(HttpServletRequest request) throws BadRequestException{
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
    
}
