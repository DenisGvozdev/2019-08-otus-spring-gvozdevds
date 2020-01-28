package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Status;

import java.util.List;

public interface StatusRepository {

    Status save(Status status);

    List<Status> findAll();

    Status findById(long id);

    boolean deleteById(long id);

    boolean updateById(Status status);
}
