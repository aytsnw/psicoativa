package com.psicoativa.service;

import com.psicoativa.dto.PsychologistDto;
import com.psicoativa.model.Psychologist;

public class PsychologistService {
    public void registerClient(PsychologistDto psyDto){

    }

    private Psychologist parseDto(PsychologistDto psyDto){
        Psychologist psy = new Psychologist();
        psy.setCrp(psyDto.getCrp());
        psy.setName(psyDto.getName());
        psy.setEmail(psyDto.getEmail());
        psy.setPhone(psyDto.getPhone());
        return psy;
    }
    
}