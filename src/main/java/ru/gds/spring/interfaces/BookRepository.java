package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Book;

import java.util.List;

@Repository
@RepositoryRestResource(path = "booksrest")
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAllByGenresId(String id);

    List<Book> findAllByAuthorsId(String id);

    List<Book> findAllByStatusId(String id);

    List<Book> findAll();

    List<Book> findByNameContainingIgnoreCase(@Param("name") String name);

    @RestResource(path = "names", rel = "names")
    List<Book> findByName(String name);
}
