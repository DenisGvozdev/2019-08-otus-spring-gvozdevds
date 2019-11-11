package ru.gds.spring.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.StatusRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

@JdbcTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/app-config.xml")
@Import(JdbcStatusRepository.class)
public class JdbcStatusRepositoryTest {

    @Autowired
    StatusRepository jdbcStatusRepository;

    @Test
    public void insertStatus() {
        Status status = new Status("archive");
        boolean result = jdbcStatusRepository.insert(status);
        assumeTrue(result);
        System.out.println("Статус добавлен: " + result);

        List<Status> statusList = jdbcStatusRepository.getAll();
        System.out.println("Все статусы: " + statusList);
    }

    @Test
    public void updateStatus() {
        Status status = jdbcStatusRepository.getById(1);
        status.setName("activeStatus");
        boolean result = jdbcStatusRepository.update(status);
        assumeTrue(result);
        System.out.println("Статус обновлен: " + result);

        status = jdbcStatusRepository.getById(1);
        System.out.println("Новые данные: " + status.toString());
    }

    @Test
    public void getById() {
        Status status = jdbcStatusRepository.getById(1);
        assumeTrue(status != null);
        assertEquals("active", status.getName());
        System.out.println(status.getName());
    }

    @Test
    public void getAll() {
        List<Status> statusList = jdbcStatusRepository.getAll();
        assumeTrue(statusList.size() == 2);
        System.out.println("Количество статусов: " + statusList.size());
    }

    @Test
    public void removeStatus() {
        boolean result = jdbcStatusRepository.removeById(0);
        assumeTrue(result);
        System.out.println("Статус удален: " + result);

        List<Status> statusList = jdbcStatusRepository.getAll();
        System.out.println("Все статусы: " + statusList);
    }
}
