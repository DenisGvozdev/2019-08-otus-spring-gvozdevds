package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.gds.spring.domain.AclSid;

import java.util.Optional;

public interface AclSidRepository extends JpaRepository<AclSid, Long> {

    Optional<AclSid> findBySid(@Param("sid") String sid);
}
