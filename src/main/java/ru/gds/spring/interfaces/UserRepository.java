package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gds.spring.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByLogin(String login);

    void deleteByLogin(String login);
}
