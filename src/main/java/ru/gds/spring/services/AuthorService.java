package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.dto.AuthorDto;
import ru.gds.spring.interfaces.AuthorReactiveRepository;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorService {

    private static final Logger logger = Logger.getLogger(AuthorService.class);

    private final AuthorReactiveRepository authorReactiveRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    AuthorService(AuthorReactiveRepository authorReactiveRepository,
                  AuthorRepository authorRepository,
                  BookRepository bookRepository) {
        this.authorReactiveRepository = authorReactiveRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Mono<Author> save(Author author) {
        return authorReactiveRepository.save(author);
    }

    public Mono<Author> findById(String id) {
        return authorReactiveRepository.findById(id);
    }

    public Mono<Void> delete(Author status) {
        return authorReactiveRepository.delete(status);
    }

    public Mono<Author> deleteById(String id) {
        return findById(id)
                .flatMap(author -> delete(author).then(Mono.just(author)));
    }

    public Mono<List<AuthorDto>> findAllLight() {
        return authorReactiveRepository
                .findAll()
                .map(AuthorDto::toDtoLight)
                .collect(Collectors.toList());
    }

    public Mono<List<AuthorDto>> findAllByBookId(String bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null)
            return Mono.empty();

        List<Author> authorsSelected = book.getAuthors();

        List<String> authorIds = new ArrayList<>();
        authorsSelected.forEach((e) -> authorIds.add(e.getId()));

        return authorReactiveRepository
                .findAll()
                .map(author -> AuthorDto.toDtoWithSelect(author, authorIds))
                .collect(Collectors.toList());
    }

    public List<Author> findAllByName(List<String> thirdNameList, Sort sort) {
        return authorRepository.findAllByName(thirdNameList, sort);
    }
}
