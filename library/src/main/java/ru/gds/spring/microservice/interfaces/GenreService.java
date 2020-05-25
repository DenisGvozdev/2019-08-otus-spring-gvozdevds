package ru.gds.spring.microservice.interfaces;

import ru.gds.spring.microservice.dto.GenreDto;
import ru.gds.spring.microservice.params.ParamsGenre;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAllLight();

    List<GenreDto> findAllByBookId(String bookId);

    List<GenreDto> findAllById(List<String> idList);

    GenreDto findById(String id);

    GenreDto save(ParamsGenre params);

    String deleteById(String id);
}
