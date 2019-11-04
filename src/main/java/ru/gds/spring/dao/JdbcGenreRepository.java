package ru.gds.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.GenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcGenreRepository implements GenreRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public boolean insert(String name) {
        String sql = "INSERT INTO GENRES (`NAME`) VALUES (?)";
        jdbc.update(sql, name);
        return true;
    }

    @Override
    public List<Genre> getAll() {
        String sql = "SELECT ID, `NAME` FROM GENRES";
        return jdbc.query(sql, new JdbcGenreRepository.GenreMapper());
    }

    @Override
    public Genre getById(long id) {

        String sql = "SELECT g.ID, g.NAME " +
                " FROM GENRES g " +
                " WHERE g.ID = ?";

        return jdbc.queryForObject(sql, new Object[]{id}, new JdbcGenreRepository.GenreMapper());
    }

    @Override
    public boolean removeById(long id) {
        String sql = "DELETE FROM GENRES WHERE ID = ?";
        jdbc.update(sql, id);
        return true;
    }

    @Override
    public boolean update(long id, String name) {
        String sql = "UPDATE GENRES SET `NAME` = ? WHERE ID = ?";
        jdbc.update(sql, name, id);
        return true;
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
