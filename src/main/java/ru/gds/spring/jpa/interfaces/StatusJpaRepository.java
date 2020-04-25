package ru.gds.spring.jpa.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gds.spring.jpa.domain.Status;

import java.util.List;

@Repository
public interface StatusJpaRepository extends JpaRepository<Status, Long> {

    @Query("select s from Status s where upper(s.name) = upper(:name)")
    List<Status> findAllByName(@Param("name") String name, Sort sort);
}
