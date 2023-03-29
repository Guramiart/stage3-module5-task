package com.mjc.school.service.dto.response;

import org.springframework.hateoas.RepresentationModel;

public class ResponseEntity<K> extends RepresentationModel<ResponseEntity<K>> {

    private final K id;

    public ResponseEntity(K id) {
        this.id = id;
    }

    public K getId() {
        return id;
    }
}
