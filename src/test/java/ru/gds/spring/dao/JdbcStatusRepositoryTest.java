package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.StatusRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

@JdbcTest
@Import(JdbcStatusRepository.class)
class JdbcStatusRepositoryTest {

    @Autowired
    StatusRepository jdbcStatusRepository;

    private static final Logger logger = Logger.getLogger(JdbcStatusRepositoryTest.class);

    @Test
    void insertStatus() {
        Status status = new Status("archive");
        boolean result = jdbcStatusRepository.insert(status);
        assumeTrue(result);
        logger.debug("Статус добавлен: " + result);

        List<Status> statusList = jdbcStatusRepository.getAll();
        logger.debug("Все статусы: " + statusList);
    }

    @Test
    void updateStatus() {
        Status status = jdbcStatusRepository.getById(1);
        status.setName("activeStatus");
        boolean result = jdbcStatusRepository.update(status);
        assumeTrue(result);
        logger.debug("Статус обновлен: " + result);

        status = jdbcStatusRepository.getById(1);
        logger.debug("Новые данные: " + status.toString());
    }

    @Test
    void getById() {
        Status status = jdbcStatusRepository.getById(1);
        assumeTrue(status != null);
        assertEquals("active", status.getName());
        logger.debug(status.getName());
    }

    @Test
    void getAll() {
        List<Status> statusList = jdbcStatusRepository.getAll();
        assumeTrue(statusList.size() == 2);
        logger.debug("Количество статусов: " + statusList.size());
    }

    @Test
    void removeStatus() {
        boolean result = jdbcStatusRepository.removeById(0);
        assumeTrue(result);
        logger.debug("Статус удален: " + result);

        List<Status> statusList = jdbcStatusRepository.getAll();
        logger.debug("Все статусы: " + statusList);
    }
}
