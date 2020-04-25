package ru.gds.spring.mongo.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gds.spring.mongo.domain.StatusMongo;

import java.util.List;

@Repository
public interface StatusMongoRepository extends MongoRepository<StatusMongo, String> {

    @Query("{name: ?0 }")
    List<StatusMongo> findAllByName(String name, Sort sort);
}
