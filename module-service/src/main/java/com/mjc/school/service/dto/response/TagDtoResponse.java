package com.mjc.school.service.dto.response;

public class TagDtoResponse extends ResponseEntity<Long> {
    private final String name;

    public TagDtoResponse(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
