package ru.gds.spring.microservice.interfaces;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gds.spring.microservice.domain.AclObjectIdentity;

@Repository
public interface AclObjectIdentityRepository extends MongoRepository<AclObjectIdentity, Long> {
}
