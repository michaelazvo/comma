package sk.comma.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import sk.comma.entity.Hodnotenie;

import java.util.List;

public class HodnotenieDaoImpl implements HodnotenieDao {

    private JdbcTemplate jdbcTemplate;
    private HodnotenieDao hodnotenieDao = DaoFactory.INSTANCE.getHodnotenieDao();

    public HodnotenieDaoImpl(JdbcTemplate jdbcTemplate) {
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
