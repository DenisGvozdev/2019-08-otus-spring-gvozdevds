package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Comment;

import java.util.List;

public interface CommentRepository {

    Comment save(Comment comment);

    List<Comment> findAll();

    Comment findById(long id);

    boolean deleteById(long id);

    boolean updateById(Comment comment);

    List<Comment> findByBookId(long bookId);
}
