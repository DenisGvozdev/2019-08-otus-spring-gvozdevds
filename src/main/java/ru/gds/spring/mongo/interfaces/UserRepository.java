package ru.gds.spring.mongo.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gds.spring.mongo.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ 'username': ?0 }")
    User findByUsername(@Param("username") String username);

    void deleteByUsername(String username);
}
