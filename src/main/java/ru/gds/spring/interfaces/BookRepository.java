package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "books-entity-graph")
    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findAll();

    @EntityGraph(value = "books-entity-graph")
    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findByNameContainingIgnoreCase(@Param("name") String name);

    @SuppressWarnings("unchecked")
    @PreAuthorize("hasRole('WRITE')")
    Book save(@Param("book") Book book);

}
