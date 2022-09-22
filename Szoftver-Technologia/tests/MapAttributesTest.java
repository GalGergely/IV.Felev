package tests;

import org.junit.Test;
import ourstd.model.map.*;

import static org.junit.Assert.*;

public class MapAttributesTest {

    @Test
    public void isAvailableTest(){
        MapAttributes waterTest = new Water();
        MapAttributes landTest = new Land();
        MapAttributes castleTest = new Castle(1);
        MapAttributes mountainTest = new Mountain();

        assertTrue(landTest.isAvailable());
        assertFalse(waterTest.isAvailable());
        assertFalse(castleTest.isAvailable());
        assertFalse(mountainTest.isAvailable());
    }

    @Test
    public void getAttributeTest(){
        MapAttributes waterTest = new Water();
        MapAttributes landTest = new Land();
        MapAttributes castleTest = new Castle(1);
        MapAttributes mountainTest = new Mountain();
        assertEquals("water",waterTest.getAttribute());
        assertEquals("land",landTest.getAttribute());
        assertEquals("castle1",castleTest.getAttribute());
        assertEquals("mountain",mountainTest.getAttribute());
    }

}

