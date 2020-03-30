package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.gds.spring.domain.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    User findByLogin(String login);

    void deleteByLogin(String login);
}
