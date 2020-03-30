package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import ru.gds.spring.domain.Book;

public interface BookReactiveRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findByNameContainingIgnoreCase(@Param("name") String name);

}
