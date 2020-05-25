package ru.gds.spring.microservice.interfaces;

import ru.gds.spring.microservice.dto.StatusDto;
import ru.gds.spring.microservice.params.ParamsStatus;

import java.util.List;

public interface StatusService {
    List<StatusDto> findAllLight();

    List<StatusDto> findAllByBookId(String bookId);

    StatusDto findById(String id);

    StatusDto save(ParamsStatus params);

    String deleteById(String id);
}
