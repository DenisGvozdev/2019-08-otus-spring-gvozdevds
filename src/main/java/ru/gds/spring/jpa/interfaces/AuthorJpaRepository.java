package ru.gds.spring.jpa.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gds.spring.jpa.domain.Author;

import java.util.List;

@Repository
public interface AuthorJpaRepository extends JpaRepository<Author, Long> {

    @Query("select a from Author a where upper(concat(a.firstName, a.secondName, a.thirdName)) = upper(:fio)")
    List<Author> findAllByFirstNameAndSecondNameAndThirdName(@Param("fio") String fio);
}


