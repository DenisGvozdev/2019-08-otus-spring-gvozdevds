package ru.gds.spring.jpa.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.jpa.domain.Book;

import java.util.List;

@Repository
public interface BookJpaRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByName(String name);
}
