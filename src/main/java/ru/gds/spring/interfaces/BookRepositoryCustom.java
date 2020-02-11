package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gds.spring.domain.Book;

public interface BookRepositoryCustom {

    @Query("select b from Book b where b.id = :id")
    Book findById(@Param("id") long id);
}
