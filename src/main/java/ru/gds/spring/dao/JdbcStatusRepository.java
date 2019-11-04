package ru.gds.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.StatusRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcStatusRepository implements StatusRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public boolean insert(String name) {
        String sql = "INSERT INTO STATUSES (`NAME`) VALUES (?)";
        jdbc.update(sql, name);
        return true;
    }

    @Override
    public List<Status> getAll() {
        String sql = "SELECT ID, `NAME` FROM STATUSES";
        return jdbc.query(sql, new JdbcStatusRepository.StatusMapper());
    }

    @Override
    public Status getById(long id) {

        String sql = "SELECT s.ID, s.NAME " +
                " FROM STATUSES s " +
                " WHERE s.ID = ?";

        return jdbc.queryForObject(sql, new Object[]{id}, new JdbcStatusRepository.StatusMapper());
    }

    @Override
    public boolean removeById(long id) {
        String sql = "DELETE FROM STATUSES WHERE ID = ?";
        jdbc.update(sql, id);
        return true;
    }

    @Override
    public boolean update(long id, String name) {
        String sql = "UPDATE STATUSES SET `NAME` = ? WHERE ID = ?";
        jdbc.update(sql, name, id);
        return true;
    }

    private static class StatusMapper implements RowMapper<Status> {
        @Override
        public Status mapRow(ResultSet resultSet, int i) throws SQLException {

            long id = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");

            return new Status(id, name);
        }
    }
}
