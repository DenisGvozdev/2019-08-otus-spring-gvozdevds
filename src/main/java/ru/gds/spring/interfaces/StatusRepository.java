package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Status;

import java.util.List;

@Repository
public interface StatusRepository extends MongoRepository<Status, String> {

    @Query("{name: ?0 }")
    List<Status> findAllByName(@Param("name") String name, Sort sort);

}
