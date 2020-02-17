package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Genre;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String>, GenreRepositoryCustom<Genre, String> {

}
