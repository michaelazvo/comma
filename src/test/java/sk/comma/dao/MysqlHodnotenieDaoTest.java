package sk.comma.dao;

import org.junit.Assert;
import org.junit.Test;
import sk.comma.entity.Hodnotenie;

import java.util.ArrayList;
import java.util.List;

public class MysqlHodnotenieDaoTest {

    private HodnotenieDao hodnotenieDao = DaoFactory.INSTANCE.getHodnotenieDao();

    @Test
    public void testFindById() {
        long id = 1;

        Hodnotenie result = hodnotenieDao.findById(id);
        Hodnotenie expected = new Hodnotenie(1, 10, 5, 13);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testFindById_invalidId_returnsNull() {
        long id = -1;

        Hodnotenie result = hodnotenieDao.findById(id);

        Assert.assertNull(result);
    }

    @Test
    public void testFindById_noResultFound_returnsNull() {
        long id = 999;

        Hodnotenie result = hodnotenieDao.findById(id);

        Assert.assertNull(result);
    }

    @Test
    public void testFindBySutazIdAndKategoriaId() {

    }


    @Test
    public void testFindAllByTelesoId() {
        long id = 13;

        Hodnotenie hodnotenie1 = new Hodnotenie(1,10,5,13);
        Hodnotenie hodnotenie2 = new Hodnotenie(2,8,6,13);
        Hodnotenie hodnotenie3 = new Hodnotenie(4,9,14,13);
        Hodnotenie hodnotenie4 = new Hodnotenie(5,9,13,13);

        // Act
        List<Hodnotenie> result = hodnotenieDao.findAllByTelesoId(id);
        List<Hodnotenie> expected = new ArrayList<>();
        expected.add(hodnotenie1);
        expected.add(hodnotenie2);
        expected.add(hodnotenie3);
        expected.add(hodnotenie4);

        // Assert
        Assert.assertEquals(expected, result);
    }



    @Test
    public void testFindAll() {
        Hodnotenie hodnotenie1 = new Hodnotenie(1,	10,	5,	13);
        Hodnotenie hodnotenie2 = new Hodnotenie(2,	8,	6,	13);
        Hodnotenie hodnotenie3 = new Hodnotenie(3,	6,	13,	14);
        Hodnotenie hodnotenie4 = new Hodnotenie(4,	9,	14,	13);
        Hodnotenie hodnotenie5 = new Hodnotenie(5,	9,	13,	13);
        Hodnotenie hodnotenie6 = new Hodnotenie(19,	5,	6,	14);
        List<Hodnotenie> expected = new ArrayList<>();
        expected.add(hodnotenie1);
        expected.add(hodnotenie2);
        expected.add(hodnotenie3);
        expected.add(hodnotenie4);
        expected.add(hodnotenie5);
        expected.add(hodnotenie6);
        List<Hodnotenie> result = hodnotenieDao.findAll();
        Assert.assertEquals(expected, result);
    }

    /*
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

    }

    @Test
    public void testInsertWithNullId() {
        Hodnotenie hodnotenie = new Hodnotenie();
        hodnotenie.setId(null);
        hodnotenie.setHodnotenie(5);
        hodnotenie.setPorotcaId(6L);
        hodnotenie.setTanecneTelesoId(14L);

        Hodnotenie result = hodnotenieDao.insert(hodnotenie);

        assertEquals(hodnotenie.getHodnotenie(), result.getHodnotenie());
        assertEquals(hodnotenie.getPorotcaId(), result.getPorotcaId());
        assertEquals(hodnotenie.getTanecneTelesoId(), result.getTanecneTelesoId());
        assertNotNull(result.getId());

    }

    */

    @Test
    public void testUpdate() {
        Hodnotenie input1 = new Hodnotenie();
        input1.setId(3L);
        input1.setHodnotenie(6);
        input1.setPorotcaId(13L);
        input1.setTanecneTelesoId(14L);

        Hodnotenie expected1 = new Hodnotenie();
        expected1.setId(3L);
        expected1.setHodnotenie(6);
        expected1.setPorotcaId(13L);
        expected1.setTanecneTelesoId(14L);

        Hodnotenie result1 = hodnotenieDao.update(input1);
        Assert.assertEquals(expected1, result1);

        Hodnotenie input2 = new Hodnotenie();
        input2.setId(20L);
        input2.setHodnotenie(3);

        Hodnotenie expected2 = null;

        Hodnotenie result2 = hodnotenieDao.update(input2);
        Assert.assertEquals(expected2, result2);
    }

    @Test
    public void testFindByPorotcaIdAndTelesoId() {
        Hodnotenie expected = new Hodnotenie(1,10,5,13);
        Hodnotenie result = hodnotenieDao.findByPorotcaIdAndTelesoId(5,13);
        Assert.assertEquals(expected, result);
    }

    /*
    @Test
    public void testDelete() {
        // Test case 1: Delete a valid hodnotenie
        Hodnotenie hodnotenie1 = new Hodnotenie(7,5,14,14);
        assertTrue(hodnotenieDao.delete(hodnotenie1));

        // Test case 2: Delete an invalid hodnotenie
        Hodnotenie hodnotenie2 = new Hodnotenie(20,15,30,20);
        assertFalse(hodnotenieDao.delete(hodnotenie2));
    }
    */

}