package com.psicoativa.service;

import com.psicoativa.dto.PsychologistDto;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Psychologist;
import com.psicoativa.repository.PsychologistRepository;

public class PsychologistService {
    public void savePsychologist(Psychologist psy) throws ServiceFailedException{
        PsychologistRepository pRepo = new PsychologistRepository();
        try {
            pRepo.addToDb(psy);
        } catch (InvalidDataException | DbOperationFailedException e) {
            throw new ServiceFailedException("Service failure: "+ e.getMessage());
        }
    }

    public Psychologist getPsychologist(int id){
        PsychologistRepository pRepo = new PsychologistRepository();
        try {
            return pRepo.findById(id);
        } catch (DbOperationFailedException e) {
            throw new ServiceFailedException("Service Failure: Couldn't find psychologist of id: " + id);
        }
    }
    
    public Psychologist parseDto(PsychologistDto pDto) throws InvalidDataException{
        Psychologist psy = new Psychologist();
        psy.setName(pDto.getName());
        psy.setCrp(pDto.getCrp());
        psy.setEmail(pDto.getEmail());
        psy.setPhone(pDto.getPhone());
        return psy;
    }
} 