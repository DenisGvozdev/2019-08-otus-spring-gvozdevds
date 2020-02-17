package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommentRepositoryCustom<Comment, String> {

    @Query("{ 'book.id': ?0 }")
    List<Comment> findByBookId(String bookId, Sort sort);
}
