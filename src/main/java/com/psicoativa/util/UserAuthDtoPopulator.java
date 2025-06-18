package com.psicoativa.util;

import com.psicoativa.core.Dto;
import com.psicoativa.dto.PsychologistDto;
import com.psicoativa.dto.UserAuthDto;
import com.psicoativa.exception.BadRequestException;

import com.psicoativa.model.UserAuth;
import jakarta.servlet.http.HttpServletRequest;

public class UserAuthDtoPopulator implements DtoPopulator<UserAuth>{
    @Override
    public UserAuthDto populate(HttpServletRequest request){
        UserAuthDto uDto = new UserAuthDto();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String type = request.getParameter("type");
        if (email == null || email.isEmpty()) throw new BadRequestException("empty or null parameter: email");
        if (password == null || password.isEmpty()) throw new BadRequestException("empty or null parameter: password");
        if (type == null || type.isEmpty()) throw new BadRequestException("empty or null parameter: type");
        uDto.setEmail(email);
        uDto.setPassword(password);
        uDto.setType(type);
        return uDto;
    }

    @Override
    public UserAuthDto populate(UserAuth model) {
        UserAuthDto uDto = new UserAuthDto();
        uDto.setId(model.getId());
        uDto.setEmail(model.getEmail());
        uDto.setPassword(model.getPassword());
        uDto.setType(model.getType());
        return uDto;
    }
}
