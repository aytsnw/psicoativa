package com.psicoativa.util;

import com.psicoativa.core.Dto;
import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.BadRequestException;

import com.psicoativa.model.UserAuth;
import jakarta.servlet.http.HttpServletRequest;

public class UserAuthDtoPopulator implements DtoPopulator<UserAuth>{
    private final UserAuthDto uDto;

    public UserAuthDtoPopulator(){
        this.uDto = new UserAuthDto();
    }

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

    @Override
    public UserAuthDto populate(UserAuth model) {
        uDto.setId(model.getId());
        uDto.setEmail(model.getEmail());
        uDto.setPassword(model.getPassword());
        uDto.setType(model.getType());
        return uDto;
    }
}
