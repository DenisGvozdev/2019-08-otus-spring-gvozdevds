package ru.gds.spring.mongo.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gds.spring.mongo.domain.Role;

import java.util.List;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    @Query("{role: { $in: ?0 } })")
    List<Role> findAllByRole(List<String> roles, Sort sort);

    Role findByRole(String role);
}
