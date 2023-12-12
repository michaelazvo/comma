package sk.comma.dao;

import junit.framework.TestCase;
import org.junit.Test;
import sk.comma.entity.Porotca;

public class MysqlPorotcaDaoTest extends TestCase {

    private PorotcaDao porotcaDao = DaoFactory.INSTANCE.getPorotcaDao();



    public void testIsPasswordCorrect() {
    }

    public void testExistingUser() {
    }

    @Test
    public void testIsAdmin() {
        int id = 10;
        Porotca porotca = porotcaDao.findById(1);

        assertNotNull(porotca);
        assertEquals("admin", porotca.getMeno());
        assertEquals(true, porotca.isJeAdmin());
    }
}