package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.gds.spring.domain.Genre;

public interface GenreReactiveRepository extends ReactiveMongoRepository<Genre, String> {
}
