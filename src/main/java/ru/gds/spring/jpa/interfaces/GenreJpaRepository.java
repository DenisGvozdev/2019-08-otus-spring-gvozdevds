package ru.gds.spring.jpa.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gds.spring.jpa.domain.Genre;

import java.util.List;

@Repository
public interface GenreJpaRepository extends JpaRepository<Genre, Long> {

    List<Genre> findAllById(List<String> ids);

    @Query("{name: { $in: ?0 } })")
    List<Genre> findAllByName(List<String> names, Sort sort);
}
