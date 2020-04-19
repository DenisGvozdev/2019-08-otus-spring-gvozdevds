package ru.gds.spring.jpa.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gds.spring.jpa.domain.Comment;

import java.util.List;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    @Query("{ 'book.id': ?0 }")
    List<Comment> findByBookId(String bookId, Sort sort);
}
