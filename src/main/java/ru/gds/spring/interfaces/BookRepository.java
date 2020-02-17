package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom<Book, String> {

}
