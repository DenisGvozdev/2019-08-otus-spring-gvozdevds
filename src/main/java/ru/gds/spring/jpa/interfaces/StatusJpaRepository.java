package ru.gds.spring.jpa.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gds.spring.jpa.domain.Status;

import java.util.List;

@Repository
public interface StatusJpaRepository extends JpaRepository<Status, Long> {

    @Query("{name: ?0 }")
    List<Status> findAllByName(String name, Sort sort);
}
