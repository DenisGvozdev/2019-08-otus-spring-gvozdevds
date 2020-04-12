package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Status;
import ru.gds.spring.dto.StatusDto;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.StatusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusService {

    private static final Logger logger = Logger.getLogger(StatusService.class);

    private final StatusRepository statusRepository;
    private final BookRepository bookRepository;

    StatusService(StatusRepository statusRepository, BookRepository bookRepository) {
        this.statusRepository = statusRepository;
        this.bookRepository = bookRepository;
    }

    public List<StatusDto> findAllLight() {
        try {
            return statusRepository
                    .findAll()
                    .stream()
                    .map(StatusDto::toDtoLight)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Statuses not found Error: " + e.getMessage());
        }
        return new ArrayList<StatusDto>();
    }

    public List<StatusDto> findAllByBookId(long bookId) {
        try {
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

        } catch (Exception e) {
            logger.error("Authors not found by bookId= " + bookId + " Error: " + e.getMessage());
        }
        return new ArrayList<StatusDto>();
    }

    public StatusDto getById(long id) {
        try {
            return StatusDto.toDtoLight(statusRepository.findById(id).orElse(null));
        } catch (Exception e) {
            logger.error("Authors not found Error: " + e.getMessage());
        }
        return null;
    }
}
