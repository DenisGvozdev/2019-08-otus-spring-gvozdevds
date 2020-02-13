package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gds.spring.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    @Query("select c from Comment c join fetch c.book where c.book.id = :bookId")
    List<Comment> findByBookId(@Param("bookId") long bookId);

    @Query("select c from Comment c where c.id = :id")
    Comment findById(@Param("id") long id);

}
