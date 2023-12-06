package sk.comma.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import sk.comma.entity.Sutaz;

import java.util.List;

public class MysqlSutazDao implements SutazDao {

    private JdbcTemplate jdbcTemplate;
    private SutazDao sutazDao = DaoFactory.INSTANCE.getSutazDao();

    public MysqlSutazDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Sutaz findById(long id) {
        return null;
    }

    @Override
    public List<Sutaz> findAll() {
        return null;
    }

    @Override
    public Sutaz insert(Sutaz sutaz) {
        return null;
    }

    @Override
    public Sutaz update(Sutaz sutaz) {
        return null;
    }

    @Override
    public boolean delete(Sutaz sutaz) {
        return false;
    }
}
