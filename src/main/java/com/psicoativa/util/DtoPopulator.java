package com.psicoativa.util;

import jakarta.servlet.http.HttpServletRequest;

public interface DtoPopulator<T> {
    public T populate(HttpServletRequest request);
}
