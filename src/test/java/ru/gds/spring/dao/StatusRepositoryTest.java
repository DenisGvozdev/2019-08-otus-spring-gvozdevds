package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.StatusRepository;
import ru.gds.spring.util.PrintUtils;

import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring.mongo"})
class StatusRepositoryTest {

    @Autowired
    StatusRepository statusRepository;

    private static final Logger logger = Logger.getLogger(StatusRepositoryTest.class);

    @Test
    void insertStatusTest() {
        Status status = new Status("archive");
        statusRepository.save(status);
        logger.debug("Статус добавлен");

        List<Status> statusList = getStatusList();
        logger.debug("Все статусы: " + statusList);
        assumeTrue(statusList.size() == 3);
    }

    @Test
    void updateStatusTest() {
        String statusName = "activeStatus";
        Status status = getFirstStatus();
        status.setName(statusName);
        status = statusRepository.save(status);
        logger.debug("Статус обновлен");
        assumeTrue(statusName.equals(status.getName()));

        status = getStatusById(status.getId());
        logger.debug("Новые данные: " + PrintUtils.printObject(null, status));
    }

    @Test
    void deleteStatusTest() {
        Status status = getFirstStatus();
        statusRepository.deleteById(status.getId());
        logger.debug("Статус удален");

        List<Status> statusList = getStatusList();
        logger.debug("Все статусы: " + statusList);
        assumeTrue(statusList.size() == 1);
    }

    private Status getStatusById(String id) {
        return statusRepository.findById(id).get();
    }

    private Status getFirstStatus() {
        List<Status> statusList = getStatusList();
        return statusList.get(0);
    }

    private List<Status> getStatusList() {
        return statusRepository.findAll();
    }
}
