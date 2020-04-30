package ru.gds.spring.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.gds.spring.interfaces.UserRepository;

@Component
public class CountUsersHealthIndicator implements HealthIndicator {

    private final UserRepository userRepository;

    public CountUsersHealthIndicator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Health health() {
        long countUsers = userRepository.count();
        if (countUsers == 0) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Нет ни одного пользователя")
                    .build();
        } else {
            return Health.up().withDetail("message", "Количество зарегистированных пользователей: " + countUsers).build();
        }
    }
}
