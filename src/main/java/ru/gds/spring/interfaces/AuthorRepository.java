package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {

    @Query("{_id: { $in: ?0 } })")
    List<Author> findAllById(@Param("id") List<String> id);

    @Query("{thirdName: { $in: ?0 } })")
    List<Author> findAllByName(List<String> names, Sort sort);
}
