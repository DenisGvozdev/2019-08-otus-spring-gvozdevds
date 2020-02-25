package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
