package ru.gds.spring.mongo.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gds.spring.mongo.domain.GenreMongo;

import java.util.List;

@Repository
public interface GenreMongoRepository extends MongoRepository<GenreMongo, String> {

    List<GenreMongo> findAllById(List<String> ids);

    @Query("{name: { $in: ?0 } })")
    List<GenreMongo> findAllByName(List<String> names, Sort sort);
}
