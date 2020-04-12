package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.AclEntry;

@Repository
public interface AclEntryRepository extends JpaRepository<AclEntry, Long> {

    @Query("select max(a.aceOrder) from AclEntry a")
    int findMaxAceOrder();
}
