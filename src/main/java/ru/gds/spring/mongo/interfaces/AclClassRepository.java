package ru.gds.spring.mongo.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import ru.gds.spring.mongo.domain.AclClass;

public interface AclClassRepository extends MongoRepository<AclClass, Long> {

    AclClass findByClasss(@Param("classs") String classs);

}
