package com.psicoativa.util;

import com.psicoativa.core.Dto;
import jakarta.servlet.http.HttpServletRequest;

public interface DtoPopulator<T> {
    public Dto populate(HttpServletRequest request);
    public Dto populate(T model);
}
