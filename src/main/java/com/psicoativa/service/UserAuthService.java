package com.psicoativa.service;

import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.UserAuth;
import com.psicoativa.repository.UserAuthRepository;

public class UserAuthService {
    public void saveUser(UserAuth user){
         UserAuthRepository uRepo = new UserAuthRepository();
        try {
            uRepo.addToDb(user);
        } catch (InvalidDataException | DbOperationFailedException e) {
            throw new ServiceFailedException("Service failure: "+ e.getMessage());
        }
    }

    public UserAuth parseUserAuthDto(UserAuthDto uDto){
        UserAuth uAuth = new UserAuth();
        uAuth.setEmail(uDto.getEmail());
        uAuth.setType(uDto.getType());
        uAuth.setPassword(uDto.getPassword());
        return uAuth;
    }
}
