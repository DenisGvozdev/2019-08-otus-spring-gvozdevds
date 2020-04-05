package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.dto.BookDto;
import ru.gds.spring.interfaces.*;
import ru.gds.spring.params.ParamsBook;
import ru.gds.spring.util.CommonUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger logger = Logger.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final StatusRepository statusRepository;

    BookService(BookRepository bookRepository,
                GenreRepository genreRepository,
                AuthorRepository authorRepository,
                StatusRepository statusRepository) {

        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.statusRepository = statusRepository;
    }

    public List<BookDto> findAllLight() {
        try {
            return bookRepository
                    .findAll()
                    .stream()
                    .map(BookDto::toDtoLight)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return new ArrayList<>();
    }

    public BookDto findById(long id) {
        try {
            if (id == 0)
                return new BookDto();

            List<Author> authors = authorRepository.findAll();
            List<Genre> genres = genreRepository.findAll();
            List<Status> statuses = statusRepository.findAll();

            return BookDto.toDto(
                    bookRepository.findById(id).orElse(new Book()),
                    authors,
                    genres,
                    statuses);
        } catch (Exception e) {
            logger.error("Book not found by id= " + id + ". Error: " + e.getMessage());
        }
        return new BookDto();
    }

    public List<BookDto> findByParam(Long id, String name) {
        try {
            if (id != null) {
                return findAllById(id);

            } else if (!StringUtils.isEmpty(name)) {
                return findByNameLight(name);
            }
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return new ArrayList<BookDto>();
    }

    private List<BookDto> findAllById(Long id) {
        List<BookDto> bookDtoList = new ArrayList<>();
        try {
            List<Author> authors = authorRepository.findAll();
            List<Genre> genres = genreRepository.findAll();
            List<Status> statuses = statusRepository.findAll();

            List<Long> idList = new ArrayList<>();
            idList.add(id);
            bookRepository
                    .findAllById(idList)
                    .forEach((book) -> bookDtoList.add(BookDto.toDto(book, authors, genres, statuses)));
        } catch (Exception e) {
            logger.error("Book not found by id= " + id + ". Error: " + e.getMessage());
        }
        return bookDtoList;
    }

    private List<BookDto> findByNameLight(String name) {
        List<BookDto> bookDtoList = new ArrayList<>();
        try {
            bookRepository
                    .findByNameContainingIgnoreCase(name)
                    .forEach((book) -> bookDtoList.add(BookDto.toDtoLight(book)));
        } catch (Exception e) {
            logger.error("Book not found by name= " + name + ". Error: " + e.getMessage());
        }
        return bookDtoList;
    }

    public BookDto save(ParamsBook params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getName()))
                throw new Exception("Book name is empty");

            List<Genre> genres = genreRepository.findAllById(CommonUtils.convertStringToArrayList(params.getGenreIds()));
            List<Author> authors = authorRepository.findAllById(CommonUtils.convertStringToArrayList(params.getAuthorIds()));

            Status status = statusRepository.findById(params.getStatusId()).orElse(null);
            byte[] image = (params.getFile() != null) ? params.getFile().getBytes() : null;

            checkStatusGenresAuthors(status, genres, authors);

            Book book;
            if (params.getId() == 0) {
                book = new Book(
                        params.getName(),
                        new Date(),
                        params.getDescription(),
                        image,
                        new HashSet<Genre>(genres),
                        new HashSet<Author>(authors),
                        status);
            } else {
                book = bookRepository.findById(params.getId()).orElse(null);
                if (book == null)
                    throw new Exception("Book not found");

                book.setName(params.getName());
                book.setDescription(params.getDescription());
                book.setGenres(new HashSet<Genre>(genres));
                book.setAuthors(new HashSet<Author>(authors));
                book.setStatus(status);
                book.setImage((image != null && image.length > 0) ? image : book.getImage());
            }
            return BookDto.toDtoLight(bookRepository.save(book));

        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());
        }
        return new BookDto();
    }

    public List<BookDto> findAll() {
        try {
            return bookRepository
                    .findAll()
                    .stream()
                    .map(BookDto::toDtoLight)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return new ArrayList<>();
    }

    public List<Book> findAllByName(String name) {
        try {
            return bookRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return new ArrayList<>();
    }

    public boolean deleteById(Long id) {
        try {
            if (id == null)
                return false;

            bookRepository.deleteById(id);
            return true;

        } catch (Exception e) {
            logger.error("Book not found");
        }
        return false;
    }

    private static void checkStatusGenresAuthors(Status status, List<Genre> genres, List<Author> authors) throws Exception {
        if (status == null)
            throw new Exception("Status not found");

        if (genres == null || genres.isEmpty())
            throw new Exception("Genre not found");

        if (authors == null || authors.isEmpty())
            throw new Exception("Author not found");
    }
}
