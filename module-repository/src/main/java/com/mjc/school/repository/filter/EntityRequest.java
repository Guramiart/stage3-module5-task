package com.mjc.school.repository.filter;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public record EntityRequest(Pageable pageable, Specification searchSpecification) {

}
