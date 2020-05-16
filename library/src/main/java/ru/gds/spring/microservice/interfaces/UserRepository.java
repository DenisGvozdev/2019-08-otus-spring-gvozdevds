package ru.gds.spring.microservice.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gds.spring.microservice.domain.User;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ 'username': ?0 }")
    User findByUsername(@Param("username") String username);

    void deleteByUsername(String username);

    @Query("{ 'roles.role': ?0 }")
    List<User> findAllByRolesRole(String role);
}
