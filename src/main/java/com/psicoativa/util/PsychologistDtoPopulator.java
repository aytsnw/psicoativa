package com.psicoativa.util;

import com.psicoativa.core.Dto;
import com.psicoativa.dto.PsychologistDto;
import com.psicoativa.exception.BadRequestException;

import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.model.Appointment;
import com.psicoativa.model.Psychologist;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public class PsychologistDtoPopulator implements DtoPopulator<Psychologist>{
    @Override
    public PsychologistDto populate(HttpServletRequest request) throws BadRequestException, InvalidDataException {
        PsychologistDto pDto = new PsychologistDto();
        String name = request.getParameter("name");
        String crp = request.getParameter("crp");
        String phone = request.getParameter("phone");
        String email =request.getParameter("email");
        if (name == null || name.isEmpty()) throw new BadRequestException("Bad request: 'name' is empty");
        if (crp == null || crp.isEmpty()) throw new BadRequestException("Bad request: 'crp' is empty");
        if (phone == null || phone.isEmpty()) throw new BadRequestException("Bad request: 'phone' is empty");
        if (email == null || email.isEmpty()) throw new BadRequestException("Bad request: 'email' is empty");
        pDto.setName(name);
        pDto.setCrp(crp);
        pDto.setPhone(phone);
        pDto.setEmail(email);
        return pDto;
    }

    @Override
    public PsychologistDto populate(Psychologist model) {
        PsychologistDto pDto = new PsychologistDto();
        pDto.setId(model.getId());
        pDto.setName(model.getName());
        pDto.setCrp(model.getCrp());
        pDto.setPhone(model.getPhone());
        pDto.setEmail(model.getEmail());
        List<Integer> appointments = new ArrayList<>();
        for (Appointment a : model.getAppointments()){
            appointments.add(a.getId());
        }
        pDto.setAppointments(appointments);
        return pDto;
    }
}
