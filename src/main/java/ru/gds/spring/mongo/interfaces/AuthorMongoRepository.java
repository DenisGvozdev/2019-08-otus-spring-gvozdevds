package ru.gds.spring.mongo.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gds.spring.mongo.domain.AuthorMongo;


import java.util.List;

@Repository
public interface AuthorMongoRepository extends MongoRepository<AuthorMongo, String> {

    @Query("{_id: { $in: ?0 } })")
    List<AuthorMongo> findAllById(List<String> ids, Sort sort);

    @Query("{thirdName: { $in: ?0 } })")
    List<AuthorMongo> findAllByName(List<String> names, Sort sort);
}
