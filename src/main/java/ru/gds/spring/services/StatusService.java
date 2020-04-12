package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Status;
import ru.gds.spring.dto.StatusDto;
import ru.gds.spring.interfaces.BookReactiveRepository;
import ru.gds.spring.interfaces.StatusReactiveRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class StatusService {

    private static final Logger logger = Logger.getLogger(StatusService.class);

    private final StatusReactiveRepository statusReactiveRepository;
    private final BookReactiveRepository bookReactiveRepository;

    StatusService(StatusReactiveRepository statusReactiveRepository, BookReactiveRepository bookReactiveRepository) {
        this.statusReactiveRepository = statusReactiveRepository;
        this.bookReactiveRepository = bookReactiveRepository;
    }

    public Mono<Status> save(Status status) {
        try {
            return statusReactiveRepository.save(status);
        } catch (Exception e) {
            logger.error("Status not found");
        }
        return Mono.empty();
    }

    public Mono<Status> findById(String id) {
        try {
            return statusReactiveRepository.findById(id);
        } catch (Exception e) {
            logger.error("Status not found");
            return Mono.empty();
        }
    }

    public Flux<Status> findStatusByIdList(List<String> idList) {
        try {
            return statusReactiveRepository.findAllById(idList);
        } catch (Exception e) {
            logger.error("Status not found");
            return Flux.empty();
        }
    }

    public Mono<Void> delete(Status status) {
        try {
            return statusReactiveRepository.delete(status);
        } catch (Exception e) {
            logger.error("Status not found");
            return Mono.empty();
        }
    }

    public Mono<Void> deleteById(String id) {
        try {
            return findById(id).flatMap(status -> delete(status).then(Mono.empty()));
        } catch (Exception e) {
            logger.error("Status not found");
            return Mono.empty();
        }
    }

    public Flux<StatusDto> findAllLight() {
        try {
            return statusReactiveRepository
                    .findAll()
                    .map(StatusDto::toDtoLight);
        } catch (Exception e) {
            logger.error("Status not found");
        }
        return Flux.empty();
    }

    public Flux<StatusDto> findAllByBookId(String bookId) {
        try {
            Mono<Book> bookMono = bookReactiveRepository.findById(bookId);
            Flux<Status> statusFlux = statusReactiveRepository.findAll();
            return statusFlux.flatMap(status -> bookMono.map(book -> StatusDto.toDtoWithSelect(status, book.getStatus().getId())));
        } catch (Exception e) {
            logger.error("Status not found");
        }
        return Flux.empty();
    }

    public Flux<Status> findAllByName(String name) {
        try {
            return statusReactiveRepository.findAllByName(name);
        } catch (Exception e) {
            logger.error("Status not found error: " + Arrays.asList(e.getStackTrace()));
            return Flux.empty();
        }
    }
}
