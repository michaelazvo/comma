package sk.comma.dao;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import sk.comma.entity.Hodnotenie;
import sk.comma.entity.Kategoria;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.function.Executable;

import static org.junit.Assert.*;

public class MysqlKategoriaDaoTest {

    private KategoriaDao kategoriaDao;


    public MysqlKategoriaDaoTest() {
        DaoFactory.INSTANCE.setTestovaciaDatabaza(true);
        kategoriaDao = DaoFactory.INSTANCE.getKategoriaDao();
    }

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
        DaoFactory.INSTANCE.setTestovaciaDatabaza(false);
    }
    @Test
    public void testFindById() {

        long existingId = 3;
        Kategoria expectedKategoria = new Kategoria(3L, "Breakdance", "Deti", "Skupina");
        Kategoria actualKategoria = kategoriaDao.findById(existingId);
        assertEquals(expectedKategoria, actualKategoria);


    }


    @Test
    public void testFindAll() {
        Kategoria kategoria1 = new Kategoria(1L, "Breakdance", "Deti", "Solo");
        Kategoria kategoria2 = new Kategoria(2L,	"Breakdance",	"Juniori",	"Skupina");
        Kategoria kategoria3 = new Kategoria(3L, 	"Breakdance",	"Deti",	"Skupina");
        Kategoria kategoria4 = new Kategoria(4L,	"Disco Dance", "Deti",	"Formacia");
        Kategoria kategoria5 = new Kategoria(5L,	"Show Dance", "Deti",	"Formacia");

        List<Kategoria> expected = new ArrayList<>();
        expected.add(kategoria5);
        expected.add(kategoria4);
        expected.add(kategoria3);
        expected.add(kategoria2);
        expected.add(kategoria1);

        List<Kategoria> result = kategoriaDao.findAll();
        assertEquals(expected, result);
    }
    @Test
    public void testInsert() {
        Kategoria kategoria = new Kategoria();
        kategoria.setId(6L);
        kategoria.setStyl("Contemporary");
        kategoria.setVekovaSkupina("Juniori");
        kategoria.setVelkostnaSkupina("Skupina");

        Kategoria result = kategoriaDao.insert(kategoria);

        assertEquals(kategoria, result);

    }

}