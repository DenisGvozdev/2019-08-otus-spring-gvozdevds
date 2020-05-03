package ru.gds.spring.mongo.interfaces;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.mongo.domain.AclObjectIdentity;

@Repository
public interface AclObjectIdentityRepository extends MongoRepository<AclObjectIdentity, Long> {
}
