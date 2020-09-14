package ru.gds.spring.microservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.microservice.domain.BookContent;

import java.util.List;

@Repository
public interface BookContentRepository extends MongoRepository<BookContent, String> {

    @SuppressWarnings("NullableProblems")
    List<BookContent> findAll();

    BookContent findByBookId(String bookId);

    void deleteByBookId(String bookId);
}
