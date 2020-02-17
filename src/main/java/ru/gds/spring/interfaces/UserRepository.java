package ru.gds.spring.interfaces;

import org.springframework.data.repository.CrudRepository;
import ru.gds.spring.domain.User;

public interface UserRepository extends CrudRepository<User, String>, UserRepositoryCustom {

}
