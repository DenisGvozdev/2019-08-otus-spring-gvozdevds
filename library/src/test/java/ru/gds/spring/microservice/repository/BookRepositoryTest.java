package ru.gds.spring.microservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.gds.spring.microservice.domain.Author;
import ru.gds.spring.microservice.domain.Book;
import ru.gds.spring.microservice.domain.Genre;
import ru.gds.spring.microservice.domain.Status;
import ru.gds.spring.microservice.interfaces.BookRepository;
import ru.gds.spring.microservice.util.FileUtils;

import java.util.Date;
import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
@AutoConfigureTestDatabase
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    //@Test
    void insertBookTest() {
        Book book = new Book(
                "Мастер и Маргарита",
                new Date(),
                "Классика",
                FileUtils.getFileBytes("images/MBulgakov_MasterIMargarita.jpg"),
                getGenreList(),
                getAuthorList(),
                getFirstStatus());
        book = bookRepository.save(book);
        assumeTrue(book.getId() != null);
    }

    //@Test
    void updateBookTest() {
        Book book = getFirstBook();
        assumeTrue(book != null);

        String bookId = book.getId();
        String bookName = "Кольцо тьмы обновление";
        book.setName(bookName);
        book.setDescription("Сказки");
        book.setGenres(getGenreList());
        book.setAuthors(getAuthorList());
        book.setStatus(getFirstStatus());
        book.setImage(FileUtils.getFileBytes("images/NPerumov_KoltsoTmy.jpg"));
        book = bookRepository.save(book);
        assumeTrue(bookName.equals(book.getName()));
    }

    //@Test
    void deleteBookTest() {
        int countAuthors = getAuthorList().size();
        int countGenres = getGenreList().size();
        int countStatuses = getStatusList().size();

        Book book = getFirstBook();
        assumeTrue(book != null);

        bookRepository.deleteById(book.getId());
        book = getBookById(book.getId());
        assumeTrue(book == null);

        // Убеждаемся что не отработало каскадное удаление
        assumeTrue(getAuthorList().size() == countAuthors);
        assumeTrue(getGenreList().size() == countGenres);
        assumeTrue(getStatusList().size() == countStatuses);
    }

    private Book getBookById(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    private Book getFirstBook() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.get(0);
    }

    private List<Author> getAuthorList() {
        Query query = Query.query(Criteria.where("thirdName").is("Перумов"));
        return mongoTemplate.find(query, Author.class);
    }

    private List<Genre> getGenreList() {
        Query query = Query.query(Criteria.where("name").is("Фэнтези"));
        return mongoTemplate.find(query, Genre.class);
    }

    private Status getFirstStatus() {
        List<Status> list = mongoTemplate.findAll(Status.class, "statuses");
        return (list.isEmpty()) ? null : list.get(0);
    }

    private List<Status> getStatusList() {
        return mongoTemplate.findAll(Status.class, "statuses");
    }
}
