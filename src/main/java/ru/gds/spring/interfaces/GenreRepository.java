package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gds.spring.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long>, GenreRepositoryCustom {

}
