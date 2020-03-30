package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.gds.spring.domain.Author;

public interface AuthorReactiveRepository extends ReactiveMongoRepository<Author, String> {
}
