package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Status;

@Repository
public interface StatusReactiveRepository extends ReactiveMongoRepository<Status, String> {

}
