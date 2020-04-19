package ru.gds.spring.jpa.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.jpa.domain.Author;

@Repository
public interface AuthorJpaRepository extends JpaRepository<Author, Long> {
}
