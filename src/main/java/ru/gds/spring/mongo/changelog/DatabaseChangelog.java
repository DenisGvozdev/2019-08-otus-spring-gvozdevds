package ru.gds.spring.mongo.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.gds.spring.domain.*;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "addStatuses", author = "dgvozdev")
    public void insertStatuses(MongoTemplate template) {
        template.save(new Status("active"));
        template.save(new Status("inactive"));
    }

    @ChangeSet(order = "002", id = "addAuthors", author = "dgvozdev")
    public void insertAuthors(MongoTemplate template) {
        template.save(new Author("Ник", "", "Перумов", new Date()));
        template.save(new Author("Даниэль", "", "Дефо", new Date()));
        template.save(new Author("Михаил", "Афанасьевич", "Булгаков", new Date()));
    }

    @ChangeSet(order = "003", id = "addGenres", author = "dgvozdev")
    public void insertGenres(MongoTemplate template) {
        template.save(new Genre("Фэнтези"));
        template.save(new Genre("Приключения"));
        template.save(new Genre("Роман"));
    }

    @ChangeSet(order = "004", id = "addBooks", author = "dgvozdev")
    public void insertBooks(MongoTemplate template) {

        List<Genre> genres = template.findAll(Genre.class);
        List<Author> authors = template.findAll(Author.class);
        Status statusActive = template.findOne(query(where("name").is("active")), Status.class);

        template.save(new Book(
                "Кольцо тьмы",
                new Date(),
                "Магия, миры, приключения...",
                null,
                genres,
                authors,
                statusActive));
        template.save(new Book(
                "Робинзон Крузо",
                new Date(),
                "Как выжить на необитаемом острове",
                null,
                genres,
                authors,
                statusActive));
    }

    @ChangeSet(order = "005", id = "addComments", author = "dgvozdev")
    public void insertComments(MongoTemplate template) {
        Book book = template.findOne(query(where("name").is("Кольцо тьмы")), Book.class);
        template.save(new Comment(book, "Хорошая книга", new Date()));
        template.save(new Comment(book, "Интересная книга", new Date()));
    }

    @ChangeSet(order = "006", id = "addUsers", author = "dgvozdev")
    public void insertUsers(MongoTemplate template) {
        template.save(new User(
                "ivanovii",
                "password",
                "ivan@mail.ru",
                "+79672522523",
                "Иванов",
                "Иван",
                "Иванович"));
    }
}
