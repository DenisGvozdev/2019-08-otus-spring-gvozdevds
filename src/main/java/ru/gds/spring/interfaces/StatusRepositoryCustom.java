package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gds.spring.domain.Status;

public interface StatusRepositoryCustom {

    @Query("select s from Status s where s.id = :id")
    Status findById(@Param("id") long id);
}
