package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAllByGenresId(String id);

    List<Book> findAllByAuthorsId(String id);

    List<Book> findAllByStatusId(String id);
}
