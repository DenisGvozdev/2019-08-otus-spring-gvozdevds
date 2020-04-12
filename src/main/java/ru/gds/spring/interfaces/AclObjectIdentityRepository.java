package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.AclObjectIdentity;

@Repository
public interface AclObjectIdentityRepository extends JpaRepository<AclObjectIdentity, Long> {
}
