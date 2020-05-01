package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Genre;

import java.util.List;

@Repository
@RepositoryRestResource(path = "genresrest")
public interface GenreRepository extends MongoRepository<Genre, String> {

    List<Genre> findAllById(List<String> ids);

    @Query("{name: { $in: ?0 } })")
    List<Genre> findAllByName(List<String> names, Sort sort);

    @RestResource(path = "names", rel = "names")
    List<Genre> findByName(String name);
}
