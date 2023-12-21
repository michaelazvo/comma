package sk.comma.dao;

import org.junit.After;

import org.junit.Before;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sk.comma.entity.Porotca;

import java.util.ArrayList;
import java.util.List;


public class MysqlPorotcaDaoTest extends TestCase {


    private PorotcaDao porotcaDao = DaoFactory.INSTANCE.getPorotcaDao();

    @Test
    public void testFindById() {
        long id = 6;
        Porotca result = porotcaDao.findById(id);
        Porotca expected = new Porotca(6L, "Peter", "Borovica", "peterborovica", "borovica", false);
        assertEquals(expected, result);
    }
    public void testFindById_invalidId_returnsNull() {
        long id = 999;
        Porotca result = porotcaDao.findById(id);
        Porotca expected = null;
        assertEquals(expected, result);
    }

    public void testIsPasswordCorrect() {
        String password = "borovica";
        boolean result = porotcaDao.isPasswordCorrect(password, "peterborovica");

        assertTrue(result);
    }

    public void testExistingUser() {
        String username = "peterborovica";
        boolean result = porotcaDao.existingUser("peterborovica");
        assertTrue(result);
    }

    @Test
    public void testIsAdmin() {
        int id = 10;
        Porotca porotca = porotcaDao.findById(1);

        assertNotNull(porotca);
        assertEquals("admin", porotca.getMeno());
        assertEquals(true, porotca.isJeAdmin());
    }

    public void testFindAll() {
    }

    public void testInsert() {
    }

    public void testGetPorotcoviaPreSutaz() {
        int id = 5;
        List<Porotca> result = porotcaDao.getPorotcoviaPreSutaz(id);
        Porotca porotca1 = new Porotca(8L, "Alzbeta", "Fialova", "alzbetafialova", "fialova", false);
        Porotca porotca2 = new Porotca(9L, "Kamil", "Modry", "kamilmodry", "modry", false);
        List<Porotca> expected = new ArrayList<>();
        expected.add(porotca1);
        expected.add(porotca2);
        assertEquals(expected, result);
    }

    public void testVymazPorotcuZoSutaze() {
    }

    public void testPridajPorotcuDoSutaze() {
    }

    public void testUpdate() {
    }

    public void testDelete() {
    }
}