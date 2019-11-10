package ru.gds.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.StatusRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcStatusRepository implements StatusRepository {

    private NamedParameterJdbcTemplate jdbc;

    private final String INSERT = "INSERT INTO STATUSES (`NAME`) VALUES (:NAME)";

    private final String SELECT_ALL = "SELECT ID, `NAME` FROM STATUSES";

    private final String SELECT_BY_ID = "SELECT s.ID, s.NAME FROM STATUSES s WHERE s.ID = :ID";

    String DELETE_BY_ID = "DELETE FROM STATUSES WHERE ID = :ID";

    String UPDATE_BY_ID = "UPDATE STATUSES SET `NAME` = :NAME WHERE ID = :ID";

    @Autowired
    JdbcStatusRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.jdbc = namedJdbcTemplate;
    }

    @Override
    public boolean insert(Status status) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("NAME", status.getName());
            jdbc.update(INSERT, params);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Status> getAll() {
        try {
            return jdbc.getJdbcOperations().query(SELECT_ALL, new JdbcStatusRepository.StatusMapper());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Status>();
    }

    @Override
    public Status getById(long id) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ID", id);
            return jdbc.queryForObject(SELECT_BY_ID, params, new JdbcStatusRepository.StatusMapper());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeById(long id) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ID", id);
            jdbc.update(DELETE_BY_ID, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Status status) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ID", status.getId());
            params.put("NAME", status.getName());
            jdbc.update(UPDATE_BY_ID, params);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
