package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String>, CommentRepositoryCustom<Comment, String> {

}
