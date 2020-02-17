package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BookRepositoryCustom<Book, String> {

    @Query("{_id: { $in: ?0 } })")
    List<Book> findAllById(List<String> ids, Sort sort);
}
