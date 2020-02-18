package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends BookRepositoryCustom, JpaRepository<Book, Long> {


}
