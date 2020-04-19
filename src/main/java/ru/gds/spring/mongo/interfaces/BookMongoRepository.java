package ru.gds.spring.mongo.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.mongo.domain.BookMongo;

import java.util.List;

@Repository
public interface BookMongoRepository extends MongoRepository<BookMongo, String> {

    List<BookMongo> findAllByGenresId(String id);

    List<BookMongo> findAllByAuthorsId(String id);

    List<BookMongo> findAllByStatusId(String id);
}
