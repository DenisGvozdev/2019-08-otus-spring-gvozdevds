package ru.gds.spring.mongo.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.gds.spring.mongo.domain.Author;
import ru.gds.spring.mongo.domain.Book;
import ru.gds.spring.mongo.dto.AuthorDto;
import ru.gds.spring.mongo.interfaces.AuthorRepository;
import ru.gds.spring.mongo.interfaces.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AuthorService {

    private static final Logger logger = Logger.getLogger(AuthorService.class);

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<AuthorDto> findAllLight() {
        try {
            return authorRepository.findAll()
                    .stream()
                    .map(AuthorDto::toDtoLight)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Authors not found Error: " + e.getMessage());
        }
        return new ArrayList<AuthorDto>();
    }

    public List<AuthorDto> findAllByBookId(String bookId) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);
            if (book == null)
                return new ArrayList<AuthorDto>();

            List<Author> authorsSelected = book.getAuthors();

            List<String> authorIds = new ArrayList<>();
            authorsSelected.forEach((e) -> authorIds.add(e.getId()));

            return authorRepository
                    .findAll()
                    .stream()
                    .map(author -> AuthorDto.toDtoWithSelect(author, authorIds))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Authors not found by bookId= " + bookId + " Error: " + e.getMessage());
        }
        return new ArrayList<AuthorDto>();
    }

    public List<AuthorDto> findAllById(List<String> idList) {
        try {
            return authorRepository.findAllById(idList, null)
                    .stream()
                    .map(AuthorDto::toDtoLight)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Authors not found Error: " + e.getMessage());
        }
        return new ArrayList<AuthorDto>();
    }
}
