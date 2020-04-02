package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.StatusReactiveRepository;
import ru.gds.spring.interfaces.StatusRepository;

import static org.mockito.Mockito.when;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class StatusServiceTest {

    @Autowired
    StatusService statusService;

    @Mock
    StatusReactiveRepository statusReactiveRepository;

    @Autowired
    StatusRepository statusRepository;

    private static final Logger logger = Logger.getLogger(StatusServiceTest.class);

    @Test
    void insertStatusTest() {
        Mono<Status> status = statusService.save(new Status("archive"));
        StepVerifier
                .create(status)
                .assertNext(obj -> assertNotNull(obj.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void updateStatusTest() {
        Status status = getStatusByName("inactive");
        status.setName("activeStatus");
        Mono<Status> statusUpd = statusService.save(status);
        StepVerifier
                .create(statusUpd)
                .assertNext(obj -> assertEquals("activeStatus", obj.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    void deleteStatusTest() {
        Status status = getStatusByName("temporary");
        when(statusReactiveRepository.findById(status.getId())).thenReturn(Mono.just(status));
        when(statusReactiveRepository.delete(status)).thenReturn(Mono.empty());
        Mono<Status> actual = statusService.deleteById(status.getId());
        StepVerifier
                .create(actual)
                .expectNext(status)
                .verifyComplete();
    }

    private Status getStatusByName(String name) {
        List<Status> statusList = statusRepository.findAllByName(name, null);
        return statusList.get(0);
    }
}
