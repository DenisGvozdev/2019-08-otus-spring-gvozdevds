package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    Comment findById(long id);

    List<Comment> findByBookId(long bookId);
}
