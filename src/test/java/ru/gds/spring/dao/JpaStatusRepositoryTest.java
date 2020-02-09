package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.StatusRepository;
import ru.gds.spring.util.PrintUtils;

import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataJpaTest
@Import(JpaStatusRepository.class)
class JpaStatusRepositoryTest {

    @Autowired
    StatusRepository jpaStatusRepository;

    private static final Logger logger = Logger.getLogger(JpaStatusRepositoryTest.class);

    @Test
    void insertStatusTest() {

        Status status = new Status("archive");
        status = jpaStatusRepository.save(status);
        long id = status.getId();
        boolean result = id > 0;
        logger.debug("Статус добавлен: " + result);
        assumeTrue(result);

        List<Status> statusList = jpaStatusRepository.findAll();
        logger.debug("Все статусы: " + statusList);
        assumeTrue(statusList.size() == 3);
    }

    @Test
    void updateStatusTest() {

        List<Status> statusList = jpaStatusRepository.findAll();
        logger.debug("Все статусы: " + statusList);
        assumeTrue(statusList.size() == 2);

        Status status = statusList.get(1);
        status.setName("activeStatus");
        boolean result = jpaStatusRepository.updateById(status);
        logger.debug("Статус обновлен: " + result);
        assumeTrue(result);

        status = jpaStatusRepository.findById(status.getId());
        logger.debug("Новые данные: " + PrintUtils.printObject(null, status));
    }

    @Test
    void deleteStatusTest() {

        Status status = new Status("archive");
        status = jpaStatusRepository.save(status);
        assumeTrue(status.getId() > 0);

        boolean result = jpaStatusRepository.deleteById(status.getId());
        logger.debug("Статус удален: " + result);
        assumeTrue(result);

        List<Status> statusList = jpaStatusRepository.findAll();
        logger.debug("Все статусы: " + statusList);
        assumeTrue(statusList.size() == 2);
    }
}
