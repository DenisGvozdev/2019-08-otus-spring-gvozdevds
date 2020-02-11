package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gds.spring.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

}
