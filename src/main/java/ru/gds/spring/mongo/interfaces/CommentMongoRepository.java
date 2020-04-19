package ru.gds.spring.mongo.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gds.spring.mongo.domain.CommentMongo;

import java.util.List;

@Repository
public interface CommentMongoRepository extends MongoRepository<CommentMongo, String> {

    @Query("{ 'book.id': ?0 }")
    List<CommentMongo> findByBookId(String bookId, Sort sort);
}
