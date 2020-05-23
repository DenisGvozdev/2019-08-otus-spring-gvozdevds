package ru.gds.spring.microservice.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.gds.spring.microservice.constant.Constant;
import ru.gds.spring.microservice.dto.BookDto;
import ru.gds.spring.microservice.dto.UserDto;
import ru.gds.spring.microservice.params.ParamsBook;
import ru.gds.spring.microservice.params.ParamsBookContent;
import ru.gds.spring.microservice.util.CommonUtils;
import ru.gds.spring.microservice.domain.*;
import ru.gds.spring.microservice.interfaces.*;
import ru.gds.spring.microservice.util.FileUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = Logger.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final StatusRepository statusRepository;
    private final CommonUtils commonUtils;

    BookServiceImpl(BookRepository bookRepository,
                    GenreRepository genreRepository,
                    AuthorRepository authorRepository,
                    StatusRepository statusRepository,
                    CommonUtils commonUtils) {

        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.statusRepository = statusRepository;
        this.commonUtils = commonUtils;
    }

    public List<BookDto> findAllLight() {
        try {
            UserDto userDto = commonUtils.getCurrentUser();
            boolean write = (UserDto.findRole("ROLE_ADMINISTRATION", userDto.getRoles())
                    || UserDto.findRole("ROLE_BOOKS_WRITE", userDto.getRoles()));

            return bookRepository
                    .findAll()
                    .stream()
                    .map(book -> BookDto.toDtoLight(book, write))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return new ArrayList<>();
    }

    public BookDto findById(String id) {
        try {
            if (StringUtils.isEmpty(id))
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

    public List<BookDto> findByParam(String id, String name) {
        try {
            if (!StringUtils.isEmpty(id)) {
                return findAllById(id);

            } else if (!StringUtils.isEmpty(name)) {
                return findByNameLight(name);
            }
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return new ArrayList<>();
    }

    private List<BookDto> findAllById(String id) {
        List<BookDto> bookDtoList = new ArrayList<>();
        try {
            List<Author> authors = authorRepository.findAll();
            List<Genre> genres = genreRepository.findAll();
            List<Status> statuses = statusRepository.findAll();

            List<String> idList = new ArrayList<>();
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
            UserDto userDto = commonUtils.getCurrentUser();
            boolean write = (UserDto.findRole("ROLE_ADMINISTRATION", userDto.getRoles())
                    || UserDto.findRole("ROLE_BOOKS_WRITE", userDto.getRoles()));

            bookRepository
                    .findByNameContainingIgnoreCase(name)
                    .forEach((book) -> bookDtoList.add(BookDto.toDtoLight(book, write)));
        } catch (Exception e) {
            logger.error("Book not found by name= " + name + ". Error: " + e.getMessage());
        }
        return bookDtoList;
    }

    public BookDto save(ParamsBook params) {
        BookDto bookDto = new BookDto();
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getName()))
                throw new Exception("Book name is empty");

            List<Genre> genres = genreRepository.findAllById(commonUtils.convertStringToListString(params.getGenreIds()), null);
            List<Author> authors = authorRepository.findAllById(commonUtils.convertStringToListString(params.getAuthorIds()), null);

            Status status = statusRepository.findById(params.getStatusId()).orElse(null);
            byte[] image = (params.getFileTitle() != null) ? params.getFileTitle().getBytes() : null;

            checkStatusGenresAuthors(status, genres, authors);

            Book book;
            if (StringUtils.isEmpty(params.getId()) || "undefined".equals(params.getId())) {
                book = new Book(
                        params.getName(),
                        new Date(),
                        params.getDescription(),
                        image,
                        genres,
                        authors,
                        status);

                book = bookRepository.save(book);

                UserDto userDto = commonUtils.getCurrentUser();
                boolean write = (UserDto.findRole("ROLE_ADMINISTRATION", userDto.getRoles())
                        || UserDto.findRole("ROLE_BOOKS_WRITE", userDto.getRoles()));

                bookDto = BookDto.toDtoLight(book, write);

            } else {
                book = bookRepository.findById(params.getId()).orElse(null);
                if (book == null)
                    throw new Exception("Book not found");

                book.setName(params.getName());
                book.setDescription(params.getDescription());
                book.setGenres(genres);
                book.setAuthors(authors);
                book.setStatus(status);
                book.setImage((image != null && image.length > 0) ? image : book.getImage());

                UserDto userDto = commonUtils.getCurrentUser();
                boolean write = (UserDto.findRole("ROLE_ADMINISTRATION", userDto.getRoles())
                        || UserDto.findRole("ROLE_BOOKS_WRITE", userDto.getRoles()));

                bookDto = BookDto.toDtoLight(bookRepository.save(book), write);
            }

        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());
            bookDto.setStatus(Constant.ERROR);
            bookDto.setMessage(e.getMessage());
        }
        return bookDto;
    }

    public List<Book> findAllByName(String name) {
        try {
            return bookRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return new ArrayList<>();
    }

    public String deleteById(String id) {
        try {
            if (id == null)
                return "Book id is null";

            bookRepository.deleteById(id);
            return "Книга успешно удалена";

        } catch (Exception e) {
            return "Ошибка удаления книги: " + e.getMessage();
        }
    }

    private static void checkStatusGenresAuthors(Status status, List<Genre> genres, List<Author> authors) throws Exception {
        if (status == null)
            throw new Exception("Status not found");

        if (genres == null || genres.isEmpty())
            throw new Exception("Genre not found");

        if (authors == null || authors.isEmpty())
            throw new Exception("Author not found");
    }

    public ParamsBookContent prepareRequestForAddBookContent(ParamsBook params, BookDto bookDto) {
        try {
            MultipartFile fileTitle = params.getFileTitle();
            if (fileTitle == null)
                fileTitle = FileUtils.byteArrayToMultipartFile(bookDto.getImage(), bookDto.getId());

            ParamsBookContent paramsBookContent = new ParamsBookContent();
            paramsBookContent.setBookId(bookDto.getId());
            paramsBookContent.setBookName(params.getName());
            paramsBookContent.setStartPage(0);
            paramsBookContent.setCountPages(0);
            paramsBookContent.setFileTitle(fileTitle);
            paramsBookContent.setFileContent(params.getFileContent());
            return paramsBookContent;

        } catch (Exception e) {
            logger.error("prepareRequestForAddBookContent error: " + Arrays.asList(e.getStackTrace()));
            return new ParamsBookContent();
        }
    }
}
