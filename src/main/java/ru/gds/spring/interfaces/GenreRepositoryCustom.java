package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GenreRepositoryCustom<Genre, String> {

    List<Genre> findAllById(List<String> ids);

    @Query("{name: { $in: ?0 } })")
    List<Genre> findAllByName(List<String> names, Sort sort);
}
