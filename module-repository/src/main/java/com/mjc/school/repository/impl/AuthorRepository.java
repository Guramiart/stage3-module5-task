package com.mjc.school.repository.impl;

import com.mjc.school.repository.AbstractRepository;
import com.mjc.school.repository.model.Author;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepository extends AbstractRepository<Author, Long> {

    @Override
    protected void updateEntity(Author prevState, Author nextState) {
        prevState.setName(nextState.getName());
        prevState.setNews(nextState.getNews());
    }

}
