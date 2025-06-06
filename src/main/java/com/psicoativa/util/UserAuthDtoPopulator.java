package com.psicoativa.util;

import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;

public class UserAuthDtoPopulator implements DtoPopulator<UserAuthDto>{
    @Override
    public UserAuthDto populate(HttpServletRequest request){
        UserAuthDto uDto = new UserAuthDto();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String type = request.getParameter("type");
        if (email.isEmpty()) throw new BadRequestException("Empty parameter: email");
        if (password.isEmpty()) throw new BadRequestException("Empty parameter: password");
        if (type.isEmpty()) throw new BadRequestException("Empty parameter: type");
        uDto.setEmail(email);
        uDto.setPassword(password);
        uDto.setType(type);
        return uDto;
    }
    
}
