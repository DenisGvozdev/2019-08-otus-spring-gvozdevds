package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.gds.spring.domain.AclClass;

import java.util.Optional;

public interface AclClassRepository extends JpaRepository<AclClass, Long> {

    Optional<AclClass> findByClasss(@Param("classs") String classs);

}
