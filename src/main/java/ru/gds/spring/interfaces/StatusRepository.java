package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
