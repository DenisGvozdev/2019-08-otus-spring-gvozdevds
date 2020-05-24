package ru.gds.spring.microservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import ru.gds.spring.microservice.domain.Status;
import ru.gds.spring.microservice.interfaces.StatusRepository;

import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
@AutoConfigureTestDatabase
@ImportAutoConfiguration({
        RibbonAutoConfiguration.class,
        FeignRibbonClientAutoConfiguration.class,
        FeignAutoConfiguration.class})
class StatusRepositoryTest {

    @Autowired
    StatusRepository statusRepository;

    @Test
    void insertStatusTest() {
        Status status = new Status("archive");
        status = statusRepository.save(status);
        assumeTrue(status.getId() != null);
    }

    @Test
    void updateStatusTest() {
        Status status = getStatusByName("active");
        assumeTrue(status != null);

        String statusName = "activeStatus";
        status.setName(statusName);
        status = statusRepository.save(status);
        assumeTrue(statusName.equals(status.getName()));
    }

    @Test
    void deleteStatusTest() {
        Status status = getStatusByName("Временный");
        assumeTrue(status != null);

        statusRepository.deleteById(status.getId());

        status = getStatusByName("Временный");
        assumeTrue(status == null);
    }

    private Status getStatusByName(String name) {
        List<Status> statusList = statusRepository.findAllByName(name, null);
        return (!statusList.isEmpty()) ? statusList.get(0) : null;
    }
}
