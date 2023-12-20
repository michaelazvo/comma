package sk.comma.dao;

import junit.framework.TestCase;
import org.springframework.dao.EmptyResultDataAccessException;
import sk.comma.entity.Kategoria;
import sk.comma.entity.Sutaz;

import java.time.LocalDate;

import static org.junit.Assert.assertThrows;

public class MysqlSutazDaoTest extends TestCase {

    SutazDao sutazDao = DaoFactory.INSTANCE.getSutazDao();

    public void testFindById() {
        int existingId = 3;
        Sutaz expectedSutaz = new Sutaz(3,"Shake it off", LocalDate.of(2023, 12, 11) , LocalDate.of(2023, 12, 17) );
        Sutaz actualSutaz = sutazDao.findById(existingId);
        assertEquals(expectedSutaz, actualSutaz);

        int nonExistingId = 999;
        assertThrows(EmptyResultDataAccessException.class, () -> sutazDao.findById(nonExistingId));
    }


    public void testFindAll() {
    }

    public void testInsert() {
    }

    public void testUpdate() {
    }

    public void testDelete() {
    }
}