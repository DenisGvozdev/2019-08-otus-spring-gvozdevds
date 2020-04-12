package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.dto.AuthorDto;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    public List<AuthorDto> findAllByBookId(long bookId) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);
            if (book == null)
                return new ArrayList<AuthorDto>();

            Set<Author> authorsSelected = book.getAuthors();

            List<Long> authorIds = new ArrayList<>();
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

    public List<AuthorDto> findAllById(List<Long> idList) {
        try {
            return authorRepository.findAllById(idList)
                    .stream()
                    .map(AuthorDto::toDtoLight)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Authors not found Error: " + e.getMessage());
        }
        return new ArrayList<AuthorDto>();
    }
}
