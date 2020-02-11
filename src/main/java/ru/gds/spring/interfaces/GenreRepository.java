package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>, GenreRepositoryCustom {

}
