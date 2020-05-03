package ru.gds.spring.mongo.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.gds.spring.microservice.util.CommonUtils;
import ru.gds.spring.mongo.domain.*;
import ru.gds.spring.mongo.dto.BookDto;
import ru.gds.spring.mongo.interfaces.*;
import ru.gds.spring.mongo.params.ParamsBook;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger logger = Logger.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final StatusRepository statusRepository;
    private final AclEntryRepository aclEntryRepository;
    private final AclObjectIdentityRepository aclObjectIdentityRepository;
    private final AclClassRepository aclClassRepository;
    private final AclSidRepository aclSidRepository;

    BookService(BookRepository bookRepository,
                GenreRepository genreRepository,
                AuthorRepository authorRepository,
                StatusRepository statusRepository,
                AclEntryRepository aclEntryRepository,
                AclObjectIdentityRepository aclObjectIdentityRepository,
                AclClassRepository aclClassRepository,
                AclSidRepository aclSidRepository) {

        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.statusRepository = statusRepository;
        this.aclEntryRepository = aclEntryRepository;
        this.aclObjectIdentityRepository = aclObjectIdentityRepository;
        this.aclClassRepository = aclClassRepository;
        this.aclSidRepository = aclSidRepository;
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
        return new ArrayList<BookDto>();
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

            List<Genre> genres = genreRepository.findAllById(CommonUtils.convertStringToListString(params.getGenreIds()), null);
            List<Author> authors = authorRepository.findAllById(CommonUtils.convertStringToListString(params.getAuthorIds()), null);

            Status status = statusRepository.findById(params.getStatusId()).orElse(null);
            byte[] image = (params.getFile() != null) ? params.getFile().getBytes() : null;

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
                //addAclToDataBase(book);
                return BookDto.toDtoLight(book);

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
                return BookDto.toDtoLight(bookRepository.save(book));
            }

        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());
        }
        return new BookDto();
    }

    private void addAclToDataBase(Book book) {

        String ACL_CLASS_BOOK = "ru.gds.spring.domain.Book";
        try {
            if (book != null) {

                String objectIdEdentity = book.getId();

                /*
                AclClass aclClass = aclClassRepository.findByClasss(ACL_CLASS_BOOK);
                long objectIdClass = (aclClass != null) ? aclClass.getId() : 0;

                AclSid aclSidRoleRead = aclSidRepository.findBySid("ROLE_READ");
                long sidRoleRead = (aclSidRoleRead != null) ? aclSidRoleRead.getId() : 0;

                AclSid aclSidAdmin = aclSidRepository.findBySid("admin");
                long sidAdmin = (aclSidAdmin != null) ? aclSidAdmin.getId() : 0;

                AclSid aclSidUser = aclSidRepository.findBySid("user");
                long sidUser = (aclSidUser != null) ? aclSidUser.getId() : 0;

                int maxAceOrder = aclEntryRepository.findMaxAceOrder();


                if (objectIdEdentity != null && objectIdClass > 0 && sidRoleRead > 0 && sidUser > 0) {

                    // Добавляем "объект идентификации"
                    AclObjectIdentity aclObjIdentity = new AclObjectIdentity();
                    aclObjIdentity.setObjectIdClass(objectIdClass);
                    aclObjIdentity.setObjectIdIdentity(objectIdEdentity);
                    aclObjIdentity.setOwnerSid(sidRoleRead);
                    aclObjIdentity.setEntriesInheriting(0L);
                    aclObjIdentity.setParentObject(null);
                    aclObjIdentity = aclObjectIdentityRepository.save(aclObjIdentity);

                    // Добавляем связку "объект идентификации-Субъект авторизации" для admin
                    AclEntry aclEntryAdm = new AclEntry();
                    aclEntryAdm.setAclObjectIdentity(aclObjIdentity.getId());
                    aclEntryAdm.setAceOrder(maxAceOrder + 1);
                    aclEntryAdm.setSid(sidAdmin);
                    aclEntryAdm.setMask(1);
                    aclEntryAdm.setGranting(1);
                    aclEntryAdm.setAuditSuccess(1);
                    aclEntryAdm.setAuditFailure(1);
                    aclEntryRepository.save(aclEntryAdm);

                    // Добавляем связку "объект идентификации-Субъект авторизации" для user
                    AclEntry aclEntryUsr = new AclEntry();
                    aclEntryUsr.setAclObjectIdentity(aclObjIdentity.getId());
                    aclEntryUsr.setAceOrder(maxAceOrder + 2);
                    aclEntryUsr.setSid(sidUser);
                    aclEntryUsr.setMask(1);
                    aclEntryUsr.setGranting(1);
                    aclEntryUsr.setAuditSuccess(1);
                    aclEntryUsr.setAuditFailure(1);
                    aclEntryRepository.save(aclEntryUsr);
                }*/
            }

        } catch (Exception e) {
            logger.error("addAclToDataBase error: " + Arrays.asList(e.getStackTrace()));
        }
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
}
