package sk.comma.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import sk.comma.entity.Porotca;

import java.util.List;

public class MysqlPorotcaDao implements PorotcaDao {

    private JdbcTemplate jdbcTemplate;
    private PorotcaDao porotcaDao = DaoFactory.INSTANCE.getPorotcaDao();

    public MysqlPorotcaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Porotca findById(long id) {
        return null;
    }

    @Override
    public List<Porotca> findAll() {
        return null;
    }

    @Override
    public Porotca insert(Porotca porotca) {
        return null;
    }

    @Override
    public Porotca update(Porotca porotca) {
        return null;
    }

    @Override
    public boolean delete(Porotca porotca) {
        return false;
    }
}
