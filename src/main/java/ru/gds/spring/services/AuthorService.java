package ru.gds.spring.services;

import org.springframework.stereotype.Component;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.dto.AuthorDto;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    AuthorService(AuthorRepository authorRepository,
                  BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<AuthorDto> findAllLight() {
        return authorRepository
                .findAll()
                .stream()
                .map(AuthorDto::toDtoLight)
                .collect(Collectors.toList());
    }

    public List<AuthorDto> findAllByBookId(long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null)
            return new ArrayList<>();

        Set<Author> authorsSelected = book.getAuthors();

        List<Long> authorIds = new ArrayList<>();
        authorsSelected.forEach((e) -> authorIds.add(e.getId()));

        return authorRepository
                .findAll()
                .stream()
                .map(author -> AuthorDto.toDtoWithSelect(author, authorIds))
                .collect(Collectors.toList());
    }

    public List<Author> findAllById(List<Long> ids) {
        return authorRepository.findAllById(ids);
    }
}
