package ru.gds.spring.microservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.gds.spring.microservice.domain.Role;
import ru.gds.spring.microservice.domain.User;
import ru.gds.spring.microservice.interfaces.UserRepository;

import java.util.List;

import static org.junit.Assume.assumeTrue;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
@AutoConfigureTestDatabase
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void insertUserTest() {
        User user = new User("testUser",
                "password",
                "email",
                "+79696544785",
                "Фамилия",
                "Имя",
                "Отчество",
                getRoles());
        user = userRepository.save(user);
        assumeTrue(user.get_id() != null);
    }

    @Test
    void updateUserTest() {
        User user = getUserByName("test");
        assumeTrue(user != null);

        String phone = "+79996663322";
        user.setPhone(phone);
        user = userRepository.save(user);
        assumeTrue(phone.equals(user.getPhone()));
    }

    @Test
    void deleteUserTest() {
        User user = getUserByName("test");
        assumeTrue(user != null);

        userRepository.deleteByUsername(user.getUsername());

        user = getUserByName("test");
        assumeTrue(user == null);
    }

    private User getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    private List<Role> getRoles() {
        return mongoTemplate.find(query(where("role").is("ROLE_BOOKS_READ")), Role.class);
    }
}
