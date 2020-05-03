package ru.gds.spring.mongo.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import ru.gds.spring.mongo.domain.AclSid;

public interface AclSidRepository extends MongoRepository<AclSid, Long> {

    AclSid findBySid(@Param("sid") String sid);
}
