package com.mjc.school.service.dto.response;

public class AuthorDtoResponse extends ResponseEntity<Long> {

    private final String name;

    public AuthorDtoResponse(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
