package com.psicoativa.service;

import org.mindrot.jbcrypt.BCrypt;

import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.exception.WrongCredentialsException;
import com.psicoativa.model.UserAuth;
import com.psicoativa.repository.UserAuthRepository;

public class LoginService {
    public UserAuthDto loginUser(UserAuthDto uDto){
        UserAuthRepository userAuthRepo = new UserAuthRepository();
        try{
            UserAuth userAuth = parseDto(uDto);
            userAuth = userAuthRepo.findByEmail(userAuth.getEmail());
            validatePassword(uDto, userAuth);
            return parseModel(userAuth);
        } catch (InvalidDataException | DbOperationFailedException | WrongCredentialsException e){
            throw new ServiceFailedException("Service failure: " + e.getMessage());
        }
    }

    private void validatePassword(UserAuthDto uDto, UserAuth userAuth) throws WrongCredentialsException{
        if (!(BCrypt.checkpw(uDto.getPassword(), userAuth.getPasswordHash()))) throw new WrongCredentialsException("Wrong password.");
    }

    private UserAuth parseDto(UserAuthDto uDto){
        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(uDto.getEmail());
        userAuth.setPassword(uDto.getPassword());
        userAuth.setType(uDto.getType());
        return userAuth;
    }

    private UserAuthDto parseModel(UserAuth userAuth){
        UserAuthDto uDto = new UserAuthDto();
        uDto.setId(userAuth.getId());
        uDto.setEmail(userAuth.getEmail());
        uDto.setPassword(userAuth.getPassword());
        uDto.setType(userAuth.getType());
        return uDto;
    }
}
