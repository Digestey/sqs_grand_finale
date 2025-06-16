package com.flixfix.sqs_testing.service;

import com.flixfix.sqs_testing.persistence.BookEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toBo(BookEntity entity);

    BookEntity toEntity(BookDto dto);

    List<BookDto> toBoList(List<BookEntity> entities);
}
