package ru.gds.spring.microservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.gds.spring.microservice.domain.Role;
import ru.gds.spring.microservice.interfaces.RoleRepository;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
@AutoConfigureTestDatabase
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    //@Test
    void insertRoleTest() {
        Role role = new Role("ROLE_FOR_TEST", "Тестовая роль");
        role = roleRepository.save(role);
        assumeTrue(role.getRole() != null);
    }

    //@Test
    void updateRoleTest() {
        Role role = roleRepository.findByRole("ROLE_TST");
        assumeTrue(role != null);

        String description = "Роль для тестирования";
        role.setDescription(description);
        role = roleRepository.save(role);
        assumeTrue(description.equals(role.getDescription()));
    }

    //@Test
    void deleteRoleTest() {
        Role role = roleRepository.findByRole("ROLE_TST");
        assumeTrue(role != null);

        roleRepository.deleteByRole(role.getRole());

        role = roleRepository.findByRole("ROLE_TST");
        assumeTrue(role == null);
    }
}
