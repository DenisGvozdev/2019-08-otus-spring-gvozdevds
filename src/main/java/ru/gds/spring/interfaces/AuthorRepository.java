package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gds.spring.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>, AuthorRepositoryCustom {
}
