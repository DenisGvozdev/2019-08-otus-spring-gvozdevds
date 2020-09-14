package ru.gds.spring.mongo.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.gds.spring.microservice.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@ChangeLog(order = "001")
public class DatabaseChangelog {

//    @ChangeSet(order = "001", id = "addStatuses", author = "dgvozdev")
//    public void insertStatuses(MongoTemplate template) {
//        template.save(new Status("active"));
//        template.save(new Status("inactive"));
//        template.save(new Status("Временный"));
//    }
//
//    @ChangeSet(order = "002", id = "addAuthors", author = "dgvozdev")
//    public void insertAuthors(MongoTemplate template) {
//        template.save(new Author("Ник", "", "Перумов", new Date()));
//        template.save(new Author("Даниэль", "", "Дефо", new Date()));
//        template.save(new Author("Михаил", "Афанасьевич", "Булгаков", new Date()));
//        template.save(new Author("Временный", "Временный", "Временный", new Date()));
//    }
//
//    @ChangeSet(order = "003", id = "addGenres", author = "dgvozdev")
//    public void insertGenres(MongoTemplate template) {
//        template.save(new Genre("Фэнтези"));
//        template.save(new Genre("Приключения"));
//        template.save(new Genre("Роман"));
//        template.save(new Genre("Временный"));
//    }
//
//    @ChangeSet(order = "004", id = "addBooks", author = "dgvozdev")
//    public void insertBooks(MongoTemplate template) {
//
//        List<Genre> genres = template.find(query(where("name").is("Фэнтези")), Genre.class);
//        List<Author> authors = template.find(query(where("thirdName").is("Перумов")), Author.class);
//        Status statusActive = template.findOne(query(where("name").is("active")), Status.class);
//
//        template.save(new Book(
//                "Кольцо тьмы",
//                new Date(),
//                "Магия, миры, приключения...",
//                null,
//                genres,
//                authors,
//                statusActive));
//        template.save(new Book(
//                "Робинзон Крузо",
//                new Date(),
//                "Как выжить на необитаемом острове",
//                null,
//                genres,
//                authors,
//                statusActive));
//    }
//
//    @ChangeSet(order = "005", id = "addComments", author = "dgvozdev")
//    public void insertComments(MongoTemplate template) {
//        Book book = template.findOne(query(where("name").is("Кольцо тьмы")), Book.class);
//        template.save(new Comment(book, "Хорошая книга", new Date()));
//        template.save(new Comment(book, "Интересная книга", new Date()));
//    }
//
//    @ChangeSet(order = "006", id = "addRoles", author = "dgvozdev")
//    public void insertRoles(MongoTemplate template) {
//        template.save(new Role("ROLE_ADMINISTRATION", "Полные права"));
//        template.save(new Role("ROLE_BOOKS_WRITE", "Созлание"));
//        template.save(new Role("ROLE_BOOKS_READ", "Чтение"));
//        template.save(new Role("ROLE_AUTHORS_READ", "Чтение авторов"));
//        template.save(new Role("ROLE_AUTHORS_WRITE", "Создание авторов"));
//        template.save(new Role("ROLE_GENRES_READ", "Чтение жанров"));
//        template.save(new Role("ROLE_GENRES_WRITE", "Создание жанров"));
//        template.save(new Role("ROLE_STATUSES_READ", "Чтение статусов"));
//        template.save(new Role("ROLE_STATUSES_WRITE", "Создание статусов"));
//        template.save(new Role("ROLE_USERS_WRITE", "Создание пользователей"));
//        template.save(new Role("ROLE_ROLES_WRITE", "Создание ролей"));
//        template.save(new Role("ROLE_TST", "Тестовая роль"));
//    }
//
//    @ChangeSet(order = "007", id = "addUsers", author = "dgvozdev")
//    public void insertUsers(MongoTemplate template) {
//        List<Role> rolesAdmin = template.find(query(where("role").is("ROLE_ADMINISTRATION")), Role.class);
//        List<Role> rolesUser = template.find(query(where("role")
//                .in("ROLE_BOOKS_READ","ROLE_AUTHORS_READ","ROLE_GENRES_READ","ROLE_STATUSES_READ")), Role.class);
//        template.save(new User(
//                "admin",
//                "password",
//                "admin@mail.ru",
//                "+79672582585",
//                "Иванов",
//                "Иван",
//                "Иванович",
//                rolesAdmin));
//        template.save(new User(
//                "user",
//                "password",
//                "user@mail.ru",
//                "+79672582585",
//                "Петров",
//                "Петр",
//                "Петрович",
//                rolesUser));
//        template.save(new User(
//                "test",
//                "password",
//                "user@mail.ru",
//                "+79672582585",
//                "Тестов",
//                "Тест",
//                "Тестович",
//                rolesUser));
//    }
//
//    @ChangeSet(order = "008", id = "addUserRoles", author = "dgvozdev")
//    public void insertUserRoles(MongoTemplate template) {
//        template.save(new Role("ROLE_ADMINISTRATION", "Полные права"));
//        template.save(new Role("ROLE_WRITE", "Создание"));
//        template.save(new Role("ROLE_READ", "Чтение"));
//    }
//
//    @ChangeSet(order = "009", id = "addBookContents", author = "dgvozdev")
//    public void insertBookContents(MongoTemplate template) {
//
//        template.save(new BookContent(
//                "1",
//                "1",
//                "Тестовая книга",
//                new Date(),
//                "C:\\Users\\Денис\\Desktop\\txt и xml\\книги\\NickPerumovChernoeKopye.txt",
//                "NickPerumovChernoeKopye.txt",
//                4,
//                new ArrayList<>(Arrays.asList(
//                        "Глава 1\n" +
//                                "Начало\n" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст"
//                        ,
//                        "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст"
//                        ,
//                        "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст" +
//                                "текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст текст"))
//        ));
//    }
}
