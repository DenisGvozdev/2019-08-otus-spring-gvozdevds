package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Status;

@Repository
public interface StatusRepository extends MongoRepository<Status, String>, StatusRepositoryCustom {

}
