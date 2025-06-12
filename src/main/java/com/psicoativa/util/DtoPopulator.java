package com.psicoativa.util;

import com.psicoativa.core.Dto;
import jakarta.servlet.http.HttpServletRequest;

public interface DtoPopulator<T> {
    Dto populate(HttpServletRequest request);
    Dto populate(T model);
}
