package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {

    @Query("{_id: { $in: ?0 } })")
    List<Genre> findAllById(@Param("id") List<String> id);

    @Query("{name: { $in: ?0 } })")
    List<Genre> findAllByName(List<String> names, Sort sort);
}
