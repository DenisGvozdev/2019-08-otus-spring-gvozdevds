package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import ru.gds.spring.domain.Author;

public interface AuthorReactiveRepository extends ReactiveMongoRepository<Author, String> {

    @Query("{thirdName: ?0 }")
    Flux<Author> findAllByThirdName(@Param("name") String name);
}
