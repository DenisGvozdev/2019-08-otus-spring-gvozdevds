package ru.gds.spring.mongo.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gds.spring.mongo.domain.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {

    List<Genre> findAllById(List<String> ids, Sort sort);

    @Query("{name: { $in: ?0 } })")
    List<Genre> findAllByName(List<String> names, Sort sort);
}
