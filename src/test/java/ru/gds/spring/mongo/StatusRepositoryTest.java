package ru.gds.spring.mongo;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
//import ru.gds.spring.mongo.domain.StatusMongo;
//import ru.gds.spring.mongo.interfaces.StatusMongoRepository;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class StatusRepositoryTest {

//    @Autowired
//    StatusMongoRepository statusRepository;
//
//    @Test
//    void insertStatusTest() {
//        StatusMongo status = new StatusMongo("archive");
//        status = statusRepository.save(status);
//        assumeTrue(status.getId() != null);
//    }
//
//    @Test
//    void updateStatusTest() {
//        StatusMongo status = getStatusByName("active");
//        assumeTrue(status != null);
//
//        String statusName = "activeStatus";
//        status.setName(statusName);
//        status = statusRepository.save(status);
//        assumeTrue(statusName.equals(status.getName()));
//    }
//
//    @Test
//    void deleteStatusTest() {
//        StatusMongo status = getStatusByName("Временный");
//        assumeTrue(status != null);
//
//        statusRepository.deleteById(status.getId());
//
//        status = getStatusByName("Временный");
//        assumeTrue(status == null);
//    }
//
//    private StatusMongo getStatusByName(String name) {
//        List<StatusMongo> statusList = statusRepository.findAllByName(name, null);
//        return (!statusList.isEmpty()) ? statusList.get(0) : null;
//    }
}
