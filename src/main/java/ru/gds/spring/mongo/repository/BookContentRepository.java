package ru.gds.spring.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gds.spring.mongo.domain.BookContent;

import java.util.List;

public interface BookContentRepository extends MongoRepository<BookContent, String> {

    List<BookContent> findAll();
}
