package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import ru.gds.spring.domain.Genre;

public interface GenreReactiveRepository extends ReactiveMongoRepository<Genre, String> {

    @Query("{name: ?0 }")
    Flux<Genre> findAllByName(@Param("name") String name);
}
