package ru.gds.spring.mongo.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gds.spring.mongo.domain.AclEntry;

@Repository
public interface AclEntryRepository extends MongoRepository<AclEntry, Long> {

    @Query("select max(a.aceOrder) from AclEntry a")
    int findMaxAceOrder();
}
