package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

}
