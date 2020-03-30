package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    @Query("{ 'book.id': ?0 }")
    List<Comment> findByBookId(String bookId, Sort sort);
}
