package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gds.spring.domain.Status;

public interface StatusRepository extends JpaRepository<Status, Long>, StatusRepositoryCustom {

    Status findById(long id);
}
