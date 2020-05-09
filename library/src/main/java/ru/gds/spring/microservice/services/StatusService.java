package ru.gds.spring.microservice.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.gds.spring.microservice.domain.Book;
import ru.gds.spring.microservice.domain.Status;
import ru.gds.spring.microservice.dto.StatusDto;
import ru.gds.spring.microservice.interfaces.BookRepository;
import ru.gds.spring.microservice.interfaces.StatusRepository;
import ru.gds.spring.microservice.params.ParamsStatus;


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

    public List<StatusDto> findAllByBookId(String bookId) {
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

    public StatusDto findById(String id) {
        try {
            return StatusDto.toDtoLight(statusRepository.findById(id).orElse(null));
        } catch (Exception e) {
            logger.error("Authors not found Error: " + e.getMessage());
        }
        return null;
    }

    public StatusDto save(ParamsStatus params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getName()))
                throw new Exception("Name is empty");

            if (StringUtils.isEmpty(params.getId())) {
                return StatusDto.toDto(statusRepository.save(new Status(params.getName())));

            } else {
                Status statusOld = statusRepository.findById(params.getId()).orElse(null);
                if (statusOld == null)
                    throw new Exception("Status not found by id = " + params.getId());

                statusOld.setName(params.getName());
                return StatusDto.toDto(statusRepository.save(statusOld));
            }

        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());
        }
        return new StatusDto();
    }

    public String deleteById(String id) {
        try {
            if (StringUtils.isEmpty(id))
                return "id is empty";

            statusRepository.deleteById(id);
            return "Статус успешно удален";

        } catch (Exception e) {
            return "Ошибка удаления статуса: " + e.getMessage();
        }
    }
}
