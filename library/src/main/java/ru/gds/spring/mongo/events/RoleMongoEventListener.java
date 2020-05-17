package ru.gds.spring.mongo.events;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.gds.spring.microservice.domain.Role;
import ru.gds.spring.microservice.domain.User;
import ru.gds.spring.microservice.interfaces.UserRepository;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;

import java.util.List;

@Component
public class RoleMongoEventListener extends AbstractMongoEventListener<Role> {

    private static final Logger logger = Logger.getLogger(RoleMongoEventListener.class);

    private final UserRepository userRepository;

    RoleMongoEventListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Role> event) {
        super.onBeforeDelete(event);
        event.getDocument();
        String role = event.getSource().get("_id").toString();

        List<User> users = userRepository.findAllByRolesRole(role);
        if (!users.isEmpty())
            throw new ForeignKeyException("Error delete Role because User: "
                    + users.get(0).getUsername() + " related to Role: " + role);
        logger.debug("RoleMongoEventListener delete");
    }
}
