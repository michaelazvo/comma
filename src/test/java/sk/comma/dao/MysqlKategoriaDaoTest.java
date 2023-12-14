package sk.comma.dao;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import sk.comma.entity.Kategoria;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;

public class MysqlKategoriaDaoTest extends TestCase {

    private KategoriaDao kategoriaDao = DaoFactory.INSTANCE.getKategoriaDao();

    @Test
    public void testFindById() {
        long existingId = 11;
        Kategoria expectedKategoria = new Kategoria(11L, "Breakdance", "Deti", "Solo");
        Kategoria actualKategoria = kategoriaDao.findById(existingId);
        assertEquals(expectedKategoria, actualKategoria);

        long nonExistingId = 999;
        assertThrows(EmptyResultDataAccessException.class, () -> kategoriaDao.findById(nonExistingId));

    }

    @Test
    public void testFindAll() {
        Kategoria kategoria1 = new Kategoria(11L, "Breakdance", "Deti", "Solo");
        Kategoria kategoria2 = new Kategoria(12L,	"Breakdance",	"Juniori",	"Skupina");
        Kategoria kategoria3 = new Kategoria(13L, 	"Breakdance",	"Deti",	"Skupina");
        Kategoria kategoria4 = new Kategoria(14L,	"Disco Dance", "Deti",	"Formacia");
        List<Kategoria> expected = new ArrayList<>();
        expected.add(kategoria4);
        expected.add(kategoria3);
        expected.add(kategoria2);
        expected.add(kategoria1);

        List<Kategoria> result = kategoriaDao.findAll();
        assertEquals(expected, result);
    }
    @Test
    public void testInsert() {
    }
    @Test
    public void testUpdate() {

    }
    @Test
    public void testDelete() {
    }
}