package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gds.spring.domain.Book;

import java.util.List;

public interface BookRepositoryCustom {

    @EntityGraph(value = "books-entity-graph")
    @Query("select b from Book b "+
            " join fetch b.genres " +
            " join fetch b.authors")
    List<Book> findAll();

    @Query("select b from Book b where b.id = :id")
    Book findById(@Param("id") long id);
}
