package sk.comma.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import sk.comma.entity.TanecneTeleso;

import java.util.List;

public class MysqlTanecneTelesoDao implements TanecneTelesoDao {
    private JdbcTemplate jdbcTemplate;

    // toto mi robilo chybu ako som pracovala so MysqlSutazDao, cize to asi nema byt
    // private TanecneTelesoDao tanecneTelesoDao = DaoFactory.INSTANCE.getTanecneTelesoDao();

    public MysqlTanecneTelesoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TanecneTeleso findById(long id) {
        return null;
    }

    @Override
    public List<TanecneTeleso> findAll() {
        return null;
    }

    @Override
    public List<TanecneTeleso> findAllBySutazId(Long sutazId) {
        return null;
    }

    @Override
    public List<TanecneTeleso> findAllByKategoriaId(Long kategoriaId) {
        return null;
    }

    @Override
    public TanecneTeleso insert(TanecneTeleso tanecneTeleso) {
        return null;
    }

    @Override
    public TanecneTeleso update(TanecneTeleso tanecneTeleso) {
        return null;
    }

    @Override
    public boolean delete(TanecneTeleso tanecneTeleso) {
        return false;
    }
}
