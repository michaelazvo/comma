package sk.comma.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import sk.comma.entity.Hodnotenie;

import java.util.List;

public class MysqlHodnotenieDao implements HodnotenieDao {

    private JdbcTemplate jdbcTemplate;


    public MysqlHodnotenieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Hodnotenie findById(long id) {
        return null;
    }

    @Override
    public List<Hodnotenie> findAll() {
        return null;
    }

    @Override
    public Hodnotenie insert(Hodnotenie hodnotenie) {
        return null;
    }

    @Override
    public Hodnotenie update(Hodnotenie hodnotenie) {
        return null;
    }

    @Override
    public boolean delete(Hodnotenie hodnotenie) {
        return false;
    }
}
