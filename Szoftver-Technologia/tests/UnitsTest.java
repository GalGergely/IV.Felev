package ourstd.Tests;

import org.junit.Test;
import ourstd.model.Balance;
import ourstd.model.Position;
import ourstd.model.units.Swimmer;
import ourstd.model.units.Units;

import java.awt.*;

import static org.junit.Assert.*;


public class UnitsTest {

    @Test
    public void getBalanceTest(){
        Units testUnit = new Swimmer();
        Balance testBalance = testUnit.getBalance();
        assertEquals(testBalance,testUnit.getBalance());
    }

    @Test
    public void getLevelTest(){
        Units testUnit = new Swimmer();
        assertEquals(1,testUnit.getLevel());
    }

    @Test
    public void incrementLevelTest(){
        Units testUnit = new Swimmer();
        testUnit.incrementLevel(1);
        assertEquals(2,testUnit.getLevel());
    }

    @Test
    public void getPositionTest(){
        Position testPosition = new Position(0,0);
        Units testUnit = new Swimmer();
        testPosition=testUnit.getPosition();
        assertEquals(testPosition,testUnit.getPosition());
    }


    @Test
    public void setPositionTest(){
        Position testPosition = new Position(0,0);
        Units testUnit = new Swimmer();
        testUnit.setPosition(10,20);
        testPosition=testUnit.getPosition();
        assertEquals(testPosition,testUnit.getPosition());
    }

    @Test
    public void getAttributeTest(){
        Units testUnit = new Swimmer();
        assertEquals("swimmer",testUnit.getAttribute());
    }

    @Test
    public void setAttributeTest(){
        Units testUnit = new Swimmer();
        testUnit.setAttribute("Hello world");
        assertEquals("Hello world",testUnit.getAttribute());
    }

    @Test
    public void getPixelPositionTest(){
        Units testUnit = new Swimmer();
        Position testPosition = testUnit.getPixelPosition();
        assertEquals(testPosition,testUnit.getPixelPosition());
    }

    @Test
    public void setPixelPositionTest(){
        Units testUnit = new Swimmer();
        testUnit.setPixelPosition(1,1);
        Position testPosition = testUnit.getPixelPosition();
        assertEquals(testPosition,testUnit.getPixelPosition());
    }

    @Test
    public void getDamageTest(){
        Balance balance = new Balance();
        Units testUnit = new Swimmer();
        assertEquals(balance.getDamage(),testUnit.getDamage());
    }

    @Test
    public void setDamageTest(){
        Units testUnit = new Swimmer();
        testUnit.setDamage(11);
        assertEquals(11,testUnit.getDamage());
    }

    @Test
    public void getRangeTest(){
        Balance balance = new Balance();
        Units testUnit = new Swimmer();
        assertEquals(balance.getRange(),testUnit.getRange());
    }

    @Test
    public void setRangeTest(){

        Units testUnit = new Swimmer();
        testUnit.setDamage(11);
        assertEquals(11,testUnit.getDamage());
    }
}
