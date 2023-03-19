package com.mjc.school.service.impl;


import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.AbstractService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService
        extends AbstractService<AuthorDtoRequest, AuthorDtoResponse, Long, Author> {

    @Autowired
    protected AuthorService(AuthorRepository repository, AuthorMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getErrorMessage() {
        return ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST.getErrorMessage();
    }
}
