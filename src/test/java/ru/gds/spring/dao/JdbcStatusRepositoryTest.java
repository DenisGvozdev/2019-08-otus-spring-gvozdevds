package ru.gds.spring.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gds.spring.config.Config;
import ru.gds.spring.domain.Status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
@Import(JdbcStatusRepository.class)
public class JdbcStatusRepositoryTest {

    @Autowired
    @Qualifier("jdbcStatusRepository")
    JdbcStatusRepository jdbcStatusRepository;

    @Test
    void insertAuthor() {
        Boolean result = jdbcStatusRepository.insert("archive");
        assumeTrue(result);
        System.out.println("Статус добавлен: " + result);

        List<Status> statusList = jdbcStatusRepository.getAll();
        System.out.println("Все статусы: " + statusList);
    }

    @Test
    void updateAuthor() {
        Boolean result = jdbcStatusRepository.update(1, "activeStatus");
        assumeTrue(result);
        System.out.println("Статус обновлен: " + result);

        Status status = jdbcStatusRepository.getById(1);
        System.out.println("Новые данные: " + status.toString());
    }

    @Test
    void getById() {
        Status status = jdbcStatusRepository.getById(1);
        assumeTrue(status != null);
        assertEquals("active", status.getName());
        System.out.println(status.getName());
    }

    @Test
    void getAll() {
        List<Status> statusList = jdbcStatusRepository.getAll();
        assumeTrue(statusList.size() == 2);
        System.out.println("Количество статусов: " + statusList.size());
    }

    @BeforeAll
    static void initAll() {
        System.out.println("---Inside initAll---");
    }

    @BeforeEach
    void init() {
        System.out.println("Start...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finished...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("---Inside tearDownAll---");
    }
}
