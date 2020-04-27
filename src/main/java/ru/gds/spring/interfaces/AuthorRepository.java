package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Author;

import java.util.List;

@Repository
@RepositoryRestResource(path = "autorssrest")
public interface AuthorRepository extends MongoRepository<Author, String> {

    @Query("{_id: { $in: ?0 } })")
    List<Author> findAllById(List<String> ids, Sort sort);

    @Query("{thirdName: { $in: ?0 } })")
    List<Author> findAllByName(List<String> names, Sort sort);

    @RestResource(path = "thirdNames", rel = "thirdNames")
    List<Author> findByThirdName(String thirdName);
}
