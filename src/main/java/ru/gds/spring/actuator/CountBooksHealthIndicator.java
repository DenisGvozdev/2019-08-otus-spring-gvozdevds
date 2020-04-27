package ru.gds.spring.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.gds.spring.interfaces.BookRepository;

@Component
public class CountBooksHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    public CountBooksHealthIndicator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Health health() {
        long countBooks = bookRepository.count();
        if (countBooks == 0) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Нет ни одной книги")
                    .build();
        } else {
            return Health.up().withDetail("message", "Количество книг в БД: " + countBooks).build();
        }
    }
}
