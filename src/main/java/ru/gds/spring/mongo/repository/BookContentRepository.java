package ru.gds.spring.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.mongo.domain.BookContent;

import java.util.List;

@Repository
public interface BookContentRepository extends MongoRepository<BookContent, String> {

    @SuppressWarnings("NullableProblems")
    List<BookContent> findAll();
}
