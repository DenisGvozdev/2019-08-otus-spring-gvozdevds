package ru.gds.spring.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.User;

@Repository
@RepositoryRestResource(path = "usersrest")
public interface UserRepository extends MongoRepository<User, String> {

    @RestResource(path = "logins", rel = "logins")
    User findByLogin(String login);

    void deleteByLogin(String login);
}
