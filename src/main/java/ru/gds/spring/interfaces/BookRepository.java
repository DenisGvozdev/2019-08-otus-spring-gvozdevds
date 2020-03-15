package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "books-entity-graph")
    List<Book> findAll();

    @EntityGraph(value = "books-entity-graph")
    List<Book> findByNameContainingIgnoreCase(@Param("name") String name);

}
