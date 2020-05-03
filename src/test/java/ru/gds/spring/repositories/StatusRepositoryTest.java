package ru.gds.spring.repositories;

//import org.apache.log4j.Logger;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import ru.gds.spring.microservice.domain.Status;
//import ru.gds.spring.microservice.interfaces.StatusRepository;
//import ru.gds.spring.microservice.util.PrintUtils;
//
//import java.util.List;
//
//import static org.junit.Assume.assumeTrue;
//
//@DataJpaTest
class StatusRepositoryTest {

//    @Autowired
//    StatusRepository statusRepository;
//
//    private static final Logger logger = Logger.getLogger(StatusRepositoryTest.class);
//
//    @Test
//    void insertStatusTest() {
//        Status status = new Status("archive");
//        status = statusRepository.save(status);
//        long id = status.getId();
//        boolean result = id > 0;
//        logger.debug("Статус добавлен: " + result);
//        assumeTrue(result);
//
//        List<Status> statusList = getStatusList();
//        logger.debug("Все статусы: " + statusList);
//        assumeTrue(statusList.size() == 3);
//    }
//
//    @Test
//    void updateStatusTest() {
//        String statusName = "activeStatus";
//
//        List<Status> statusList = getStatusList();
//        logger.debug("Все статусы: " + statusList);
//        assumeTrue(statusList.size() == 2);
//
//        Status status = statusList.get(1);
//        status.setName(statusName);
//        status = statusRepository.save(status);
//        logger.debug("Статус обновлен");
//        assumeTrue(statusName.equals(status.getName()));
//
//        status = statusRepository.findById(status.getId()).get();
//        logger.debug("Новые данные: " + PrintUtils.printObject(null, status));
//    }
//
//    @Test
//    void deleteStatusTest() {
//        Status status = new Status("archive");
//        status = statusRepository.save(status);
//        assumeTrue(status.getId() > 0);
//
//        statusRepository.deleteById(status.getId());
//        logger.debug("Статус удален");
//
//        List<Status> statusList = getStatusList();
//        logger.debug("Все статусы: " + statusList);
//        assumeTrue(statusList.size() == 2);
//    }
//
//    private List<Status> getStatusList() {
//        return statusRepository.findAll();
//    }
}
