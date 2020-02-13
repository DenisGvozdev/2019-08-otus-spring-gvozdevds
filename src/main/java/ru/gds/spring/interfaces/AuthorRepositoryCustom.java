package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gds.spring.domain.Author;

public interface AuthorRepositoryCustom {

    @Query("select a from Author a where a.id = :id")
    Author findById(@Param("id") long id);
}
