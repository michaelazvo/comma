package sk.comma.dao;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import sk.comma.entity.TanecneTeleso;

import java.util.ArrayList;
import java.util.List;

public class MysqlTanecneTelesoDaoTest {

    TanecneTelesoDao tanecneTelesoDao;



    public MysqlTanecneTelesoDaoTest() {
        DaoFactory.INSTANCE.setTestovaciaDatabaza(true);
        tanecneTelesoDao = DaoFactory.INSTANCE.getTanecneTelesoDao();
    }

    @Test
    public void testFindById() {
        long existingId = 1L;
        TanecneTeleso actualTanecneTeleso = tanecneTelesoDao.findById(existingId);
        TanecneTeleso expectedTanecneTeleso = new TanecneTeleso(1L, "Hviezdicky", "Svetlo", "DanceClub", 2, 3, "456678", "hviezdicky@gmail.com",  "Peter, Martin, Jakub, Samuel" );


        Assert.assertEquals(expectedTanecneTeleso, actualTanecneTeleso);
    }

    @Test
    public void testFindAll() {
        TanecneTeleso teleso1 = new TanecneTeleso(1L, "Hviezdicky", "Svetlo", "DanceClub", 2, 3, "456678", "hviezdicky@gmail.com",  "Peter, Martin, Jakub, Samuel" );
        TanecneTeleso teleso2 = new TanecneTeleso(2L, "Mesiacikovia", "Clair de Lune", "Spirit", 4, 4, "333267","mesiacikovia@gmail.com",  "Alzbeta, Zuzana, Daniela" );
        TanecneTeleso teleso3 = new TanecneTeleso(3L, "GirlsPower", "Shake it off", "GirlsGeneration", 3, 5,"123456",  "girlspower@gmail.com", "Sofia, Monika, Bronislava" );

        List<TanecneTeleso> expected = new ArrayList<>();
        expected.add(teleso1);
        expected.add(teleso2);
        expected.add(teleso3);
        List<TanecneTeleso> result = tanecneTelesoDao.findAll();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testFindAllBySutazId() {
        int sutazId = 3;

        TanecneTeleso teleso1 = new TanecneTeleso(1L, "Hviezdicky", "Svetlo", "DanceClub", 2, 3, "456678", "hviezdicky@gmail.com",  "Peter, Martin, Jakub, Samuel" );

        List<TanecneTeleso> expected = new ArrayList<>();
        expected.add(teleso1);
        List<TanecneTeleso> result = tanecneTelesoDao.findAllBySutazId(sutazId);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testFindAllBySutazIdKategoriaId() {
        TanecneTeleso teleso1 = new TanecneTeleso(1L, "Hviezdicky", "Svetlo", "DanceClub", 2, 3, "456678", "hviezdicky@gmail.com",  "Peter, Martin, Jakub, Samuel" );
        int sutazId = 3;
        long kategoriaId = 2;
        List<TanecneTeleso> expected = new ArrayList<>();
        expected.add(teleso1);
        List<TanecneTeleso> result = tanecneTelesoDao.findAllBySutazIdKategoriaId(sutazId, kategoriaId);
        Assert.assertEquals(expected, result);
    }


    @Test
    public void testInsert() {
        TanecneTeleso teleso = new TanecneTeleso();
        teleso.setNazov("Jahodky");
        teleso.setHudba("wawa");
        teleso.setKlub("Ovocie");
        teleso.setEmail("lala@gmail.com");
        teleso.setTelefonneCislo("000444");
        teleso.setTanecnici("Alzbeta, Kamil");
        teleso.setKategoriaId(12);
        teleso.setSutazId(5);

        TanecneTeleso result = tanecneTelesoDao.insert(teleso);
        Assert.assertEquals(teleso, result);
        tanecneTelesoDao.delete(result);

    }

}