package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Status;
import ru.gds.spring.dto.StatusDto;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.StatusReactiveRepository;
import ru.gds.spring.interfaces.StatusRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StatusService {

    private static final Logger logger = Logger.getLogger(StatusService.class);

    private final StatusReactiveRepository statusReactiveRepository;
    private final StatusRepository statusRepository;
    private final BookRepository bookRepository;

    StatusService(StatusReactiveRepository statusReactiveRepository,
                  StatusRepository statusRepository,
                  BookRepository bookRepository) {
        this.statusReactiveRepository = statusReactiveRepository;
        this.statusRepository = statusRepository;
        this.bookRepository = bookRepository;
    }

    public Mono<List<StatusDto>> findAllLight() {
        return statusReactiveRepository
                .findAll()
                .map(StatusDto::toDtoLight)
                .collect(Collectors.toList());
    }

    public Mono<List<StatusDto>> findAllByBookId(String bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null || book.getStatus() == null) {
            return statusReactiveRepository
                    .findAll()
                    .map(StatusDto::toDto)
                    .collect(Collectors.toList());
        }
        Status statusSelected = book.getStatus();
        return statusReactiveRepository
                .findAll()
                .map(status -> StatusDto.toDtoWithSelect(status, statusSelected.getId()))
                .collect(Collectors.toList());
    }

    public Optional<Status> getById(String id) {
        return statusRepository.findById(id);
    }
}
