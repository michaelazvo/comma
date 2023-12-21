package sk.comma.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sk.comma.entity.Hodnotenie;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MysqlHodnotenieDaoTest {

    private HodnotenieDao hodnotenieDao;


    public MysqlHodnotenieDaoTest() {
        DaoFactory.INSTANCE.setTestovaciaDatabaza(true);
        hodnotenieDao  = DaoFactory.INSTANCE.getHodnotenieDao();

    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        DaoFactory.INSTANCE.setTestovaciaDatabaza(false);
    }

    @Test
    public void testFindById() {
        long id = 1;
        Hodnotenie result = hodnotenieDao.findById(id);
        Hodnotenie expected = new Hodnotenie(1, 8, 2, 3);

        assertEquals(expected, result);
    }

    @Test
    public void testFindById_invalidId() {
        long id = -1;

        Hodnotenie result = hodnotenieDao.findById(id);

        Assert.assertNull(result);
    }



    @Test
    public void testFindAllByTelesoId() {
        long id = 3;

        Hodnotenie hodnotenie1 = new Hodnotenie(1,8,2,3);


        List<Hodnotenie> result = hodnotenieDao.findAllByTelesoId(id);
        List<Hodnotenie> expected = new ArrayList<>();
        expected.add(hodnotenie1);


        assertEquals(expected, result);
    }


    @Test
    public void testFindAllByTelesoId_invalidId() {
        long id = 4;

        Hodnotenie hodnotenie1 = new Hodnotenie(1,8,2,3);


        List<Hodnotenie> result = hodnotenieDao.findAllByTelesoId(id);
        List<Hodnotenie> expected = new ArrayList<>();



        assertEquals(expected, result);
    }



    @Test
    public void testFindAll() {
        Hodnotenie hodnotenie1 = new Hodnotenie(1,	8,	2,	3);
        Hodnotenie hodnotenie2 = new Hodnotenie(2,	7,	3,	2);
        Hodnotenie hodnotenie3 = new Hodnotenie(3,	4,	4,	1);

        List<Hodnotenie> expected = new ArrayList<>();
        expected.add(hodnotenie1);
        expected.add(hodnotenie2);
        expected.add(hodnotenie3);

        List<Hodnotenie> result = hodnotenieDao.findAll();
        assertEquals(expected, result);
    }


    @Test
    public void testInsert() {
        Hodnotenie hodnotenie = new Hodnotenie();
        hodnotenie.setId(7L);
        hodnotenie.setHodnotenie(5);
        hodnotenie.setPorotcaId(14L);
        hodnotenie.setTanecneTelesoId(14L);

        Hodnotenie result = hodnotenieDao.insert(hodnotenie);

        assertEquals(hodnotenie.getId(), result.getId());
        assertEquals(hodnotenie.getHodnotenie(), result.getHodnotenie());
        assertEquals(hodnotenie.getPorotcaId(), result.getPorotcaId());
        assertEquals(hodnotenie.getTanecneTelesoId(), result.getTanecneTelesoId());

        hodnotenieDao.delete(result);

    }


    @Test
    public void testUpdate() {
        Hodnotenie input1 = new Hodnotenie();
        input1.setId(3L);
        input1.setHodnotenie(6);
        input1.setPorotcaId(4L);
        input1.setTanecneTelesoId(1L);

        Hodnotenie expected1 = new Hodnotenie();
        expected1.setId(3L);
        expected1.setHodnotenie(6);
        expected1.setPorotcaId(4L);
        expected1.setTanecneTelesoId(1L);

        Hodnotenie result1 = hodnotenieDao.update(input1);
        assertEquals(expected1, result1);

        Hodnotenie input2 = new Hodnotenie();
        input2.setId(20L);
        input2.setHodnotenie(3);

        Hodnotenie expected2 = null;

        Hodnotenie result2 = hodnotenieDao.update(input2);
        assertEquals(expected2, result2);
    }

    @Test
    public void testFindByPorotcaIdAndTelesoId() {
        Hodnotenie expected = new Hodnotenie(1,8,2,3);
        Hodnotenie result = hodnotenieDao.findByPorotcaIdAndTelesoId(2,3);
        assertEquals(expected, result);
    }


    @Test
    public void testDelete() {
        Hodnotenie hodnotenie1 = new Hodnotenie(3,4,4,1);

        assertTrue(hodnotenieDao.delete(hodnotenie1));

    }


}