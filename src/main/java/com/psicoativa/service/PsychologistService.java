package com.psicoativa.service;

import com.psicoativa.dto.ClientDto;
import com.psicoativa.dto.PsychologistDto;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Client;
import com.psicoativa.model.Psychologist;
import com.psicoativa.repository.PsychologistRepository;
import com.psicoativa.util.ClientDtoPopulator;
import com.psicoativa.util.PsychologistDtoPopulator;

import java.util.ArrayList;
import java.util.List;

public class PsychologistService {
    private final PsychologistRepository pRepo;

    public PsychologistService(PsychologistRepository pRepo){
        this.pRepo = pRepo;
    }

    public void savePsychologist(Psychologist psy) throws ServiceFailedException{
        try {
            pRepo.addToDb(psy);
        } catch (InvalidDataException | DbOperationFailedException e) {
            throw new ServiceFailedException("Service failure: "+ e.getMessage());
        }
    }

    public Psychologist getPsychologist(int id){
        Psychologist psy = pRepo.findById(id);
        if (psy == null) throw new ServiceFailedException("Service failure: Couldn't find psychologist of id: "+ id);
        return pRepo.findById(id);
    }

    public PsychologistDto getPsychologistDto(int id){
        PsychologistDtoPopulator pDtoPopulator = new PsychologistDtoPopulator();
        Psychologist psy = pRepo.findById(id);
        if (psy == null) throw new ServiceFailedException("Service failure: Couldn't find psychologist of id: "+ id);
        pDtoPopulator.populate(psy);
        return pDtoPopulator.populate(psy);
    }
    
    public Psychologist parseDto(PsychologistDto pDto) throws InvalidDataException{
        Psychologist psy = new Psychologist();
        psy.setName(pDto.getName());
        psy.setCrp(pDto.getCrp());
        psy.setEmail(pDto.getEmail());
        psy.setPhone(pDto.getPhone());
        return psy;
    }

    public List<PsychologistDto> getPsychologistDto(String name){
        PsychologistDtoPopulator pDtoPopulator = new PsychologistDtoPopulator();
        List<Psychologist> psychologists = pRepo.findByName(name);
        if (psychologists.isEmpty()) throw new ServiceFailedException("couldn't find any results with name: " + name);
        List<PsychologistDto> pDtos = new ArrayList<PsychologistDto>();
        for (Psychologist p : psychologists){
            pDtos.add(pDtoPopulator.populate(p));
        }
        return pDtos;
    }
} 