package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Comment;

import java.util.List;

@Repository
@RepositoryRestResource(path = "commentsrest")
public interface CommentRepository extends MongoRepository<Comment, String> {

    @Query("{ 'book.id': ?0 }")
    List<Comment> findByBookId(String bookId, Sort sort);

    @RestResource(path = "booknames", rel = "booknames")
    List<Comment> findByBook_Name(String bookName);
}
