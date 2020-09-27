package ru.gds.spring.microservice.interfaces;

import ru.gds.spring.microservice.dto.CommentDto;
import ru.gds.spring.microservice.params.ParamsBookPageComment;

import java.util.Date;
import java.util.List;

public interface CommentService {

    CommentDto save(ParamsBookPageComment params);

    CommentDto findById(String id);

    List<CommentDto> findByParam(String bookId, String username, Date createDate);
}
