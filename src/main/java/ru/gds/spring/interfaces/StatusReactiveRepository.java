package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.gds.spring.domain.Status;

@Repository
public interface StatusReactiveRepository extends ReactiveMongoRepository<Status, String> {

    @Query("{name: ?0 }")
    Flux<Status> findAllByName(@Param("name") String name);
}
