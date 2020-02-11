package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gds.spring.domain.Genre;

public interface GenreRepositoryCustom {

    @Query("select g from Genre g where g.id = :id")
    Genre findById(@Param("id") long id);
}
