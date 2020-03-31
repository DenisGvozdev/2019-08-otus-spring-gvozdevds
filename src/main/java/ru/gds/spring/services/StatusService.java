package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Status;
import ru.gds.spring.dto.StatusDto;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.StatusRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StatusService {

    private static final Logger logger = Logger.getLogger(StatusService.class);

    private final StatusRepository statusRepository;
    private final BookRepository bookRepository;

    StatusService(StatusRepository statusRepository,
                  BookRepository bookRepository) {

        this.statusRepository = statusRepository;
        this.bookRepository = bookRepository;
    }

    public List<StatusDto> findAllLight() {
        return statusRepository
                .findAll()
                .stream()
                .map(StatusDto::toDtoLight)
                .collect(Collectors.toList());
    }

    public List<StatusDto> findAllByBookId(long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null || book.getStatus() == null) {
            return statusRepository
                    .findAll()
                    .stream()
                    .map(StatusDto::toDto)
                    .collect(Collectors.toList());
        }
        Status statusSelected = book.getStatus();
        return statusRepository
                .findAll()
                .stream()
                .map(status -> StatusDto.toDtoWithSelect(status, statusSelected.getId()))
                .collect(Collectors.toList());
    }

    public Optional<Status> getById(long id) {
        return statusRepository.findById(id);
    }
}
