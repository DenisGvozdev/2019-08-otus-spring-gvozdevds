package ru.gds.spring.mongo.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.gds.spring.mongo.domain.*;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "addStatuses", author = "dgvozdev")
    public void insertStatuses(MongoTemplate template) {
        template.save(new StatusMongo("active"));
        template.save(new StatusMongo("inactive"));
        template.save(new StatusMongo("Временный"));
    }

    @ChangeSet(order = "002", id = "addAuthors", author = "dgvozdev")
    public void insertAuthors(MongoTemplate template) {
        template.save(new AuthorMongo("Ник", "", "Перумов", new Date()));
        template.save(new AuthorMongo("Даниэль", "", "Дефо", new Date()));
        template.save(new AuthorMongo("Михаил", "Афанасьевич", "Булгаков", new Date()));
        template.save(new AuthorMongo("Временный", "Временный", "Временный", new Date()));
    }

    @ChangeSet(order = "003", id = "addGenres", author = "dgvozdev")
    public void insertGenres(MongoTemplate template) {
        template.save(new GenreMongo("Фэнтези"));
        template.save(new GenreMongo("Приключения"));
        template.save(new GenreMongo("Роман"));
        template.save(new GenreMongo("Временный"));
    }

    @ChangeSet(order = "004", id = "addBooks", author = "dgvozdev")
    public void insertBooks(MongoTemplate template) {

        List<GenreMongo> genres = template.find(query(where("name").is("Фэнтези")), GenreMongo.class);
        List<AuthorMongo> authors = template.find(query(where("thirdName").is("Перумов")), AuthorMongo.class);
        StatusMongo statusActive = template.findOne(query(where("name").is("active")), StatusMongo.class);

        template.save(new BookMongo(
                "Кольцо тьмы",
                new Date(),
                "Магия, миры, приключения...",
                null,
                genres,
                authors,
                statusActive));
        template.save(new BookMongo(
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
        BookMongo book = template.findOne(query(where("name").is("Кольцо тьмы")), BookMongo.class);
        template.save(new CommentMongo(book, "Хорошая книга", new Date()));
        template.save(new CommentMongo(book, "Интересная книга", new Date()));
    }
}
