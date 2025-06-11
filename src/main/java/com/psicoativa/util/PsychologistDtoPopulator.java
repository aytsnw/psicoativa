package com.psicoativa.util;

import com.psicoativa.core.Dto;
import com.psicoativa.dto.PsychologistDto;
import com.psicoativa.exception.BadRequestException;

import com.psicoativa.model.Psychologist;
import jakarta.servlet.http.HttpServletRequest;

public class PsychologistDtoPopulator implements DtoPopulator<Psychologist>{
    private final PsychologistDto pDto;

    public PsychologistDtoPopulator(){
        this.pDto = new PsychologistDto();
    }
    @Override
    public PsychologistDto populate(HttpServletRequest request) throws BadRequestException{
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

    @Override
    public PsychologistDto populate(Psychologist model) {
        pDto.setId(model.getId());
        pDto.setName(model.getName());
        pDto.setCrp(model.getCrp());
        pDto.setPhone(model.getPhone());
        pDto.setEmail(model.getEmail());
        return pDto;
    }
}
