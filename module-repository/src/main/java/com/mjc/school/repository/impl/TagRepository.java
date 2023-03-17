package com.mjc.school.repository.impl;

import com.mjc.school.repository.AbstractRepository;
import com.mjc.school.repository.model.Tag;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepository extends AbstractRepository<Tag, Long> {
    @Override
    protected void updateEntity(Tag prevState, Tag nextState) {
        prevState.setName(nextState.getName());
        prevState.setNews(nextState.getNews());
    }

}
