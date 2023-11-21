package sk.comma.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import sk.comma.entity.Kategoria;

import java.util.List;

public class KategoriaDaoImpl implements KategoriaDao {

    private JdbcTemplate jdbcTemplate;
    private KategoriaDao kategoriaDao = DaoFactory.INSTANCE.getKategoriaDao();

    public KategoriaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Kategoria findById(long id) {
        return null;
    }

    @Override
    public List<Kategoria> findAll() {
        return null;
    }

    @Override
    public Kategoria insert(Kategoria kategoria) {
        return null;
    }

    @Override
    public Kategoria update(Kategoria kategoria) {
        return null;
    }

    @Override
    public boolean delete(Kategoria kategoria) {
        return false;
    }
}
