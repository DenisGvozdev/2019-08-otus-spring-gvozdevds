package ru.gds.spring.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.StatusRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/app-config.xml")
@Import(JdbcStatusRepository.class)
public class JdbcStatusRepositoryTest {

    @Autowired
    StatusRepository jdbcStatusRepository;

    @Test
    void insertStatus() {
        Status status = new Status("archive");
        boolean result = jdbcStatusRepository.insert(status);
        assumeTrue(result);
        System.out.println("Статус добавлен: " + result);

        List<Status> statusList = jdbcStatusRepository.getAll();
        System.out.println("Все статусы: " + statusList);
    }

    @Test
    void updateStatus() {
        Status status = jdbcStatusRepository.getById(1);
        status.setName("activeStatus");
        boolean result = jdbcStatusRepository.update(status);
        assumeTrue(result);
        System.out.println("Статус обновлен: " + result);

        status = jdbcStatusRepository.getById(1);
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

    @Test
    void removeStatus() {
        boolean result = jdbcStatusRepository.removeById(0);
        assumeTrue(result);
        System.out.println("Статус удален: " + result);

        List<Status> statusList = jdbcStatusRepository.getAll();
        System.out.println("Все статусы: " + statusList);
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
