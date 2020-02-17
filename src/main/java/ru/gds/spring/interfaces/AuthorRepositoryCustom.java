package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AuthorRepositoryCustom<Author, String> {

    @Query("{_id: { $in: ?0 } })")
    List<Author> findAllById(List<String> ids, Sort sort);

    @Query("{thirdName: { $in: ?0 } })")
    List<Author> findAllByName(List<String> names, Sort sort);
}
