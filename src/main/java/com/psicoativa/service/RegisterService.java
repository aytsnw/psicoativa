package com.psicoativa.service;

import com.psicoativa.dto.ClientDto;
import com.psicoativa.dto.PsychologistDto;
import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Client;
import com.psicoativa.model.Psychologist;
import com.psicoativa.model.UserAuth;

public class RegisterService {
    private final UserAuthService uService;
    private final ClientService cService;
    private final PsychologistService pService;

    public RegisterService(UserAuthService uService, ClientService cService, PsychologistService pService){
        this.uService = uService;
        this.cService = cService;
        this.pService = pService;
    }

    public void registerClient(UserAuthDto uDto, ClientDto cDto){
        try {
            UserAuth user = uService.parseDto(uDto);
            Client client = cService.parseDto(cDto);
            client.setUserAuth(user);
            user.setUserBase(client);
            cService.saveClient(client);
            uService.saveUser(user);
        } catch (DbOperationFailedException | InvalidDataException e) {
            throw new ServiceFailedException("Service Failure: " + e.getMessage());
        }
    }

    public void registerPsychologist(UserAuthDto uDto, PsychologistDto pDto){
        try {
            UserAuth user = uService.parseDto(uDto);
            Psychologist psy = pService.parseDto(pDto);
            psy.setUserAuth(user);
            user.setUserBase(psy);
            pService.savePsychologist(psy);
            uService.saveUser(user);
        } catch (DbOperationFailedException | InvalidDataException e) {
            throw new ServiceFailedException("Service Failure: " + e.getMessage());
        }
    }
}
