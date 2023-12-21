package sk.comma.dao;

import junit.framework.TestCase;
import org.springframework.dao.EmptyResultDataAccessException;
import sk.comma.entity.Kategoria;
import sk.comma.entity.Sutaz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

public class MysqlSutazDaoTest extends TestCase {

    SutazDao sutazDao;


    public MysqlSutazDaoTest() {
        DaoFactory.INSTANCE.setTestovaciaDatabaza(true);
        sutazDao = DaoFactory.INSTANCE.getSutazDao();
    }

    public void testFindById() {
        int existingId = 3;
        Sutaz expectedSutaz = new Sutaz(3,"PrvaSutaz", LocalDate.of(2023, 12, 16 ), LocalDate.of(2023, 12, 17) );
        Sutaz actualSutaz = sutazDao.findById(existingId);
        assertEquals(expectedSutaz, actualSutaz);

        int nonExistingId = 999;
        assertThrows(EmptyResultDataAccessException.class, () -> sutazDao.findById(nonExistingId));
    }


    public void testFindAll() {

        Sutaz expected1 = new Sutaz(3,"PrvaSutaz", LocalDate.of(2023, 12, 16 ), LocalDate.of(2023, 12, 17) );
        Sutaz expected2= new Sutaz(4,"DruhaSutaz", LocalDate.of(2023, 12, 20 ), LocalDate.of(2023, 12, 21) );
        Sutaz expected3 = new Sutaz(5,"TretiaSutaz", LocalDate.of(2023, 12, 23 ), LocalDate.of(2023, 12, 24) );

        List<Sutaz> expected = new ArrayList<>();
        expected.add(expected1);
        expected.add(expected2);
        expected.add(expected3);
        List<Sutaz> actual = sutazDao.findAll();
        assertEquals(expected,actual);


    }

    public void testInsert() {
        Sutaz sutaz = new Sutaz("Sutaz4", LocalDate.of(2023, 12, 20 ), LocalDate.of(2023, 12, 23) );


        sutazDao.insert(sutaz);

        Sutaz insertedSutaz = sutazDao.findById(sutaz.getId());

        assertEquals("Sutaz4", insertedSutaz.getNazov());
        assertEquals(LocalDate.of(2023, 12, 20), insertedSutaz.getOdDatum());
        assertEquals(LocalDate.of(2023, 12, 23), insertedSutaz.getDoDatum());
        sutazDao.delete(sutaz);
    }

    public void testUpdate() {
        Sutaz sutaz = new Sutaz("Sutaz4", LocalDate.of(2023, 12, 20 ), LocalDate.of(2023, 12, 23) );


        sutazDao.insert(sutaz);

        sutaz.setDoDatum(LocalDate.of(2023, 12, 24));
        sutazDao.update(sutaz);
        assertEquals(LocalDate.of(2023, 12, 24), sutaz.getDoDatum());
        sutazDao.delete(sutaz);



    }


}