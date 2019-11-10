package ru.gds.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.GenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcGenreRepository implements GenreRepository {

    private NamedParameterJdbcTemplate jdbc;

    private final String INSERT = "INSERT INTO GENRES (`NAME`) VALUES (:NAME)";

    private final String SELECT_ALL = "SELECT ID, `NAME` FROM GENRES";

    private final String SELECT_BY_ID = "SELECT g.ID, g.NAME FROM GENRES g WHERE g.ID = :ID";

    String DELETE_BY_ID = "DELETE FROM GENRES WHERE ID = :ID";

    String UPDATE_BY_ID = "UPDATE GENRES SET `NAME` = :NAME WHERE ID = :ID";

    @Autowired
    JdbcGenreRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.jdbc = namedJdbcTemplate;
    }

    @Override
    public boolean insert(Genre genre) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("NAME", genre.getName());
            jdbc.update(INSERT, params);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Genre> getAll() {
        try {
            return jdbc.getJdbcOperations().query(SELECT_ALL, new JdbcGenreRepository.GenreMapper());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Genre>();
    }

    @Override
    public Genre getById(long id) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ID", id);
            return jdbc.queryForObject(SELECT_BY_ID, params, new JdbcGenreRepository.GenreMapper());

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
    public boolean update(Genre genre) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ID", genre.getId());
            params.put("NAME", genre.getName());
            jdbc.update(UPDATE_BY_ID, params);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {

            long id = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");

            return new Genre(id, name);
        }
    }
}
