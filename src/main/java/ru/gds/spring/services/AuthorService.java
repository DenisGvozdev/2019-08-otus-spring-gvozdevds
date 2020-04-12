package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.dto.AuthorDto;
import ru.gds.spring.interfaces.AuthorReactiveRepository;
import ru.gds.spring.interfaces.BookReactiveRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class AuthorService {

    private static final Logger logger = Logger.getLogger(AuthorService.class);

    private final AuthorReactiveRepository authorReactiveRepository;
    private final BookReactiveRepository bookReactiveRepository;

    AuthorService(AuthorReactiveRepository authorReactiveRepository, BookReactiveRepository bookReactiveRepository) {
        this.authorReactiveRepository = authorReactiveRepository;
        this.bookReactiveRepository = bookReactiveRepository;
    }

    public Mono<Author> save(Author author) {
        try {
            return authorReactiveRepository.save(author);
        } catch (Exception e) {
            logger.error("Author save error: " + Arrays.asList(e.getStackTrace()));
        }
        return Mono.empty();
    }

    public Mono<Author> findById(String id) {
        try {
            return authorReactiveRepository.findById(id);
        } catch (Exception e) {
            logger.error("Author not found error: " + Arrays.asList(e.getStackTrace()));
        }
        return Mono.empty();
    }

    public Flux<Author> findAllByThirdName(String name) {
        try {
            return authorReactiveRepository.findAllByThirdName(name);
        } catch (Exception e) {
            logger.error("Author not found error: " + Arrays.asList(e.getStackTrace()));
        }
        return Flux.empty();
    }

    public Mono<Void> delete(Author author) {
        try {
            return authorReactiveRepository.delete(author);
        } catch (Exception e) {
            logger.error("Author not found error: " + Arrays.asList(e.getStackTrace()));
            return Mono.empty();
        }
    }

    public Mono<Void> deleteById(String id) {
        try {
            return findById(id).flatMap(author -> delete(author).then(Mono.empty()));
        } catch (Exception e) {
            logger.error("Author not found error: " + Arrays.asList(e.getStackTrace()));
            return Mono.empty();
        }
    }

    public Flux<AuthorDto> findAllLight() {
        try {
            return authorReactiveRepository
                    .findAll()
                    .map(AuthorDto::toDtoLight);
        } catch (Exception e) {
            logger.error("Author not found error: " + Arrays.asList(e.getStackTrace()));
        }
        return Flux.empty();
    }

    public Flux<AuthorDto> findAllByBookId(String bookId) {
        try {
            Mono<Book> bookMono = bookReactiveRepository.findById(bookId);
            Flux<Author> authorFlux = authorReactiveRepository.findAll();
            return authorFlux.flatMap(author -> bookMono.map(book -> AuthorDto.toDtoWithSelect(author, book.getAuthors())));
        } catch (Exception e) {
            logger.error("Author not found error: " + Arrays.asList(e.getStackTrace()));
            return Flux.empty();
        }
    }

    public Flux<Author> findAllByIdList(List<String> idList) {
        try {
            return authorReactiveRepository.findAllById(idList);
        } catch (Exception e) {
            logger.error("Author not found by idList: " + idList + ". Error: " + Arrays.asList(e.getStackTrace()));
            return Flux.empty();
        }
    }
}
