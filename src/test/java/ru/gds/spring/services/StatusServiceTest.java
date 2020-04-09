package ru.gds.spring.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.StatusReactiveRepository;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class StatusServiceTest {

    @Autowired
    StatusService statusService;

    @Mock
    StatusReactiveRepository statusReactiveRepository;

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
        Mono<Void> actual = statusService.deleteById(status.getId());
        StepVerifier
                .create(actual)
                .verifyComplete();
    }

    private Status getStatusByName(String name) {
        return statusService.findAllByName(name).blockFirst();

    }
}
