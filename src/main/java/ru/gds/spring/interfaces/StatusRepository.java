package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Status;

import java.util.List;

@Repository
@RepositoryRestResource(path = "statusesrest")
public interface StatusRepository extends MongoRepository<Status, String> {

    @Query("{name: ?0 }")
    List<Status> findAllByName(String name, Sort sort);

    @RestResource(path = "names", rel = "names")
    List<Status> findByName(String name);
}
