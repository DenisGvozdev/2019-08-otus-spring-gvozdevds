package ru.gds.spring.mongo.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.*;

import java.util.Date;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "addStatuses", author = "dgvozdev")
    public void insertStatuses(DB db) {
        DBCollection collection = db.getCollection("statuses");
        BasicDBObject doc = new BasicDBObject().append("name", "active");
        collection.insert(doc);
        doc = new BasicDBObject().append("name", "active");
        collection.insert(doc);
    }

    @ChangeSet(order = "002", id = "addAuthors", author = "dgvozdev")
    public void insertAuthors(DB db) {
        DBCollection collection = db.getCollection("authors");
        BasicDBObject doc = new BasicDBObject()
                .append("firstName", "Ник")
                .append("secondName", "")
                .append("thirdName", "Перумов")
                .append("birthDate", new Date());
        collection.insert(doc);
        doc = new BasicDBObject()
                .append("firstName", "Даниэль")
                .append("secondName", "")
                .append("thirdName", "Дефо")
                .append("birthDate", new Date());
        collection.insert(doc);
        doc = new BasicDBObject()
                .append("firstName", "Михаил")
                .append("secondName", "Афанасьевич")
                .append("thirdName", "Булгаков")
                .append("birthDate", new Date());
        collection.insert(doc);
    }

    @ChangeSet(order = "003", id = "addGenres", author = "dgvozdev")
    public void insertGenres(DB db) {
        DBCollection collection = db.getCollection("genres");
        BasicDBObject doc = new BasicDBObject().append("name", "Фэнтези");
        collection.insert(doc);
        doc = new BasicDBObject().append("name", "Приключения");
        collection.insert(doc);
        doc = new BasicDBObject().append("name", "Роман");
        collection.insert(doc);
    }

    @ChangeSet(order = "004", id = "addBooks", author = "dgvozdev")
    public void insertBooks(DB db) {
        DBCollection collectionGenres = db.getCollection("genres");
        DBCursor genres = collectionGenres.find();

        DBCollection collectionAuthors = db.getCollection("authors");
        DBCursor authors = collectionAuthors.find();

        DBCollection collectionStatuses = db.getCollection("statuses");
        DBObject status = collectionStatuses.findOne();

        DBCollection collection = db.getCollection("books");
        BasicDBObject doc = new BasicDBObject()
                .append("name", "Кольцо тьмы")
                .append("createDate", new Date())
                .append("description", "Магия, миры, приключения...")
                .append("image", null)
                .append("genres", genres)
                .append("status", status)
                .append("authors", authors);
        collection.insert(doc);
        doc = new BasicDBObject()
                .append("name", "Робинзон Крузо")
                .append("createDate", new Date())
                .append("description", "Как выжить на необитаемом острове")
                .append("image", null)
                .append("genres", genres)
                .append("status", status)
                .append("authors", authors);
        collection.insert(doc);
    }

    @ChangeSet(order = "005", id = "addComments", author = "dgvozdev")
    public void insertComments(DB db) {
        DBCollection collectionBooks = db.getCollection("books");
        DBObject book = collectionBooks.findOne();

        DBCollection collection = db.getCollection("comments");
        BasicDBObject doc = new BasicDBObject()
                .append("book", book)
                .append("comment", "Хорошая книга")
                .append("createDate", new Date());
        collection.insert(doc);
        doc = new BasicDBObject()
                .append("book", book)
                .append("comment", "Интересная книга")
                .append("createDate", new Date());
        collection.insert(doc);
    }

    @ChangeSet(order = "006", id = "addUsers", author = "dgvozdev")
    public void insertUsers(DB db) {
        DBCollection collection = db.getCollection("users");
        BasicDBObject doc = new BasicDBObject()
                .append("login", "ivanovii")
                .append("password", "password")
                .append("email", "ivan@mail.ru")
                .append("phone", "+79672522523")
                .append("firstName", "Иванов")
                .append("secondName", "Иван")
                .append("thirdName", "Иванович");
        collection.insert(doc);
    }
}
