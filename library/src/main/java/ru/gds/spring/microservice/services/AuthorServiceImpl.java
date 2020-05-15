package ru.gds.spring.microservice.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.gds.spring.microservice.constant.ConstantFormatDate;
import ru.gds.spring.microservice.interfaces.AuthorService;
import ru.gds.spring.microservice.util.DateUtils;
import ru.gds.spring.microservice.domain.Author;
import ru.gds.spring.microservice.domain.Book;
import ru.gds.spring.microservice.dto.AuthorDto;
import ru.gds.spring.microservice.interfaces.AuthorRepository;
import ru.gds.spring.microservice.interfaces.BookRepository;
import ru.gds.spring.microservice.params.ParamsAuthor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Logger logger = Logger.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
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
        return new ArrayList<>();
    }

    public List<AuthorDto> findAllByBookId(String bookId) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);
            if (book == null)
                return new ArrayList<>();

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
        return new ArrayList<>();
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
        return new ArrayList<>();
    }

    public AuthorDto findById(String id) {
        try {
            return AuthorDto.toDto(authorRepository.findById(id).orElse(null));
        } catch (Exception e) {
            logger.error("Author not found Error: " + e.getMessage());
        }
        return null;
    }

    public AuthorDto save(ParamsAuthor params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getFirstName()))
                throw new Exception("FirstName is empty");

            Date BirthDate = DateUtils.getDateFromString(params.getBirthDate(), ConstantFormatDate.FORMAT_ddMMyyyy);

            if (StringUtils.isEmpty(params.getId())) {
                return AuthorDto.toDto(authorRepository.save(
                        new Author(
                                params.getFirstName(),
                                params.getSecondName(),
                                params.getThirdName(),
                                BirthDate)));

            } else {
                Author authorOld = authorRepository.findById(params.getId()).orElse(null);
                if (authorOld == null)
                    throw new Exception("Author not found by id = " + params.getId());

                authorOld.setFirstName(params.getFirstName());
                authorOld.setSecondName(params.getSecondName());
                authorOld.setThirdName(params.getThirdName());
                authorOld.setBirthDate(BirthDate);
                return AuthorDto.toDto(authorRepository.save(authorOld));
            }

        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());
        }
        return new AuthorDto();
    }

    public String deleteById(String id) {
        try {
            if (StringUtils.isEmpty(id))
                return "id is empty";

            authorRepository.deleteById(id);
            return "Автор успешно удален";

        } catch (Exception e) {
            return "Ошибка удаления автора: " + e.getMessage();
        }
    }
}
