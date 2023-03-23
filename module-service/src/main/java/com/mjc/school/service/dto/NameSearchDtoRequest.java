package com.mjc.school.service.dto;

import org.springframework.lang.Nullable;

public class NameSearchDtoRequest extends PageDtoRequest {

    private final String name;

    public NameSearchDtoRequest(@Nullable int page, @Nullable int size, @Nullable String name) {
        super(page, size);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
