package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import ru.gds.spring.domain.Status;

import java.util.List;

public interface StatusRepositoryCustom {

    @Query("{name: ?0 }")
    List<Status> findAllByName(String name, Sort sort);
}
