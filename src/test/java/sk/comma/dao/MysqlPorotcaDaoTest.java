package sk.comma.dao;

import org.junit.After;

import org.junit.Assert;
import org.junit.Before;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import sk.comma.Hashovanie;
import sk.comma.entity.Hodnotenie;
import sk.comma.entity.Porotca;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class MysqlPorotcaDaoTest {


    private PorotcaDao porotcaDao;


    public MysqlPorotcaDaoTest() {
        DaoFactory.INSTANCE.setTestovaciaDatabaza(true);
        porotcaDao = DaoFactory.INSTANCE.getPorotcaDao();
    }
    @Test
    public void testFindById() {
        long id = 2;
        Porotca result = porotcaDao.findById(id);
        Porotca expected = new Porotca(2L, "Miska", "Hneda", "miskahneda", "$2a$10$nZOPRNyh9Y4YTbloqAMuo.6R6RO.1.ZkJowyCygzifT0GcdGWfsJW", false, "$2a$10$nZOPRNyh9Y4YTbloqAMuo.");
        Assert.assertEquals(expected, result);
    }
    @Test
    public void testFindById_invalidId() {
        long id = 999;
        Porotca result = porotcaDao.findById(id);
        Porotca expected = null;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testIsPasswordCorrect() {
        String password = "$2a$10$nZOPRNyh9Y4YTbloqAMuo.6R6RO.1.ZkJowyCygzifT0GcdGWfsJW";
        boolean result = porotcaDao.isPasswordCorrect(password, "miskahneda");

        Assert.assertTrue(result);
    }

    @Test
    public void testExistingUser() {
        String username = "miskahneda";
        boolean result = porotcaDao.existingUser("miskahneda");
        Assert.assertTrue(result);
    }

    @Test
    public void testIsAdmin() {

        Porotca porotca = porotcaDao.findById(1);

        Assert.assertNotNull(porotca);
        Assert.assertEquals("admin", porotca.getMeno());
        Assert.assertEquals(true, porotca.isJeAdmin());
    }



    @Test
    public void testGetPorotcoviaPreSutaz() {
        int id = 4;
        List<Porotca> result = porotcaDao.getPorotcoviaPreSutaz(id);
        Porotca porotca1 = new Porotca(3L, "Jozef", "Mrkva", "jozefmrkva", "$2a$10$UUT5PkgnENx5L3rxQZzezeWS9bF/GWFQQQ7AIP43HJ9n0TUQAYzia", false, "$2a$10$UUT5PkgnENx5L3rxQZzeze");
        List<Porotca> expected = new ArrayList<>();
        expected.add(porotca1);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testUpdate() {
        Porotca input1 = new Porotca();
        input1.setId(12L);
        input1.setMeno("Andrej");
        input1.setPriezvisko("Kováč");
        input1.setUzivatelskeMeno("andrejkovac");
        input1.setHeslo("$2a$10$9/D4ou0vTeqaM9z1fBZgGel9pqntTCDdphyGCbrPUagg8LJ0a3yAe");
        input1.setJeAdmin(false);
        input1.setSol("$2a$10$9/D4ou0vTeqaM9z1fBZgGe");

        porotcaDao.insert(input1);

        Porotca expected1 = new Porotca();
        expected1.setId(4L);
        expected1.setMeno("Andrejj");
        expected1.setPriezvisko("Kováč");
        expected1.setUzivatelskeMeno("andrejkovac");
        expected1.setHeslo("$2a$10$9/D4ou0vTeqaM9z1fBZgGel9pqntTCDdphyGCbrPUagg8LJ0a3yAe");
        expected1.setJeAdmin(false);
        expected1.setSol("$2a$10$9/D4ou0vTeqaM9z1fBZgGe");

        porotcaDao.update(expected1);

        assertNotEquals(input1, expected1);
        porotcaDao.delete(input1);
    }

    /*
    @Test
    public void testDelete() {

        Porotca input1 = new Porotca();
        input1.setId(8L);
        input1.setMeno("Andrej");
        input1.setPriezvisko("Mrak");
        input1.setUzivatelskeMeno("andrejmrak");
        String sol = BCrypt.gensalt();
        String heslo = "heslo";
        input1.setHeslo(Hashovanie.hashovanie(heslo, sol));
        input1.setJeAdmin(false);
        input1.setSol(sol);
        porotcaDao.insert(input1);

        boolean deleteResult = porotcaDao.delete(input1);

        Assert.assertTrue(deleteResult);

        }

     */
}