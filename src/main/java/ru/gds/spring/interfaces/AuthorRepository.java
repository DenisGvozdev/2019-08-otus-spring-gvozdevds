package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Author;

import java.util.Date;
import java.util.List;

public interface AuthorRepository {

    boolean insert(String firstName, String secondName, String thirdName, Date birthDate, long status);

    List<Author> getAll();

    Author getById(long id);

    boolean removeById(long id);

    boolean update(long id, String firstName, String secondName, String thirdName, Date birthDate, long status);
}
