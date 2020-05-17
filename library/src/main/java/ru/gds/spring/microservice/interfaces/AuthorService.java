package ru.gds.spring.microservice.interfaces;

import ru.gds.spring.microservice.dto.AuthorDto;
import ru.gds.spring.microservice.params.ParamsAuthor;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAllLight();

    List<AuthorDto> findAllByBookId(String bookId);

    List<AuthorDto> findAllById(List<String> idList);

    AuthorDto findById(String id);

    AuthorDto save(ParamsAuthor params);

    String deleteById(String id);
}
