package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Status;

import java.util.List;

public interface StatusRepository {

    boolean insert(String name);

    List<Status> getAll();

    Status getById(long id);

    boolean removeById(long id);

    boolean update(long id, String name);
}
