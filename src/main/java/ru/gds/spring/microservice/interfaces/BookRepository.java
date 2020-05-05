package ru.gds.spring.microservice.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gds.spring.microservice.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAllByGenresId(String id);

    List<Book> findAllByAuthorsId(String id);

    List<Book> findAllByStatusId(String id);


    //@PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findAll();

    //@PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findByNameContainingIgnoreCase(@Param("name") String name);

    @SuppressWarnings("unchecked")
    //@PreAuthorize("hasRole('WRITE')")
    Book save(@Param("book") Book book);
}
