package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.CreateNewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.UpdateNewsDtoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AuthorMapper.class, TagMapper.class, CommentMapper.class })
public abstract class NewsMapper {

    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected TagRepository tagRepository;
    @Autowired
    protected CommentRepository commentRepository;

    public abstract List<NewsDtoResponse> modelListToDto(List<News> modelList);

    @Mapping(source = "author", target = "authorDto")
    @Mapping(source = "tags", target = "tagsDto")
    @Mapping(target = "commentsDto", source = "comments")
    public abstract NewsDtoResponse modelToDto(News model);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "author", expression = "java(authorRepository.readByName(dto.author()).get())")
    @Mapping(target = "tags",
            expression = "java(dto.tags().stream().map(name -> tagRepository.readByName(name).get()).toList())")
    @Mapping(target = "comments",
            expression = "java(dto.commentsIds().stream().map(id -> commentRepository.getReference(id)).toList())")
    public abstract News dtoToModel(CreateNewsDtoRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "tags", expression =
            "java(dto.tags().stream().map(name -> tagRepository.readByName(name).get()).toList())")
    @Mapping(target = "comments", expression =
            "java(dto.commentsIds().stream().map(commentId -> commentRepository.getReference(commentId)).toList())")
    public abstract News dtoToModel(UpdateNewsDtoRequest dto);
}
