package tests;

import org.junit.Test;
import ourstd.model.Balance;
import ourstd.model.Game;
import ourstd.model.map.MapAttributesTypes;
import ourstd.model.units.*;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void buyFighterTest(){
        Game gameTest = new Game();
        Balance balanceTest = new Balance();
        gameTest.buyFighter(UnitTypes.ASSASSIN);
        assertEquals(balanceTest.getStartingGold()-balanceTest.getAssassinCost(),gameTest.getActivePlayer().getGold());
    }

    //ha nincs pénz nem kap a player új katonát
    @Test
    public void buyFighterTest2(){
        Game gameTest = new Game();
        Balance balanceTest = new Balance();
        gameTest.getActivePlayer().setGold(0);
        gameTest.buyFighter(UnitTypes.ASSASSIN);
        assertEquals(0, gameTest.getActivePlayer().getFighters().size());
    }

    //ha nincs pénz egy katonára nem vonódik le pénz
    @Test
    public void buyFighterTest3(){
        Game gameTest = new Game();
        Balance balanceTest = new Balance();
        gameTest.getActivePlayer().setGold(balanceTest.getAssassinCost()-1);
        gameTest.buyFighter(UnitTypes.ASSASSIN);
        assertEquals(balanceTest.getAssassinCost()-1, gameTest.getActivePlayer().getGold());
    }

    @Test
    public void isFighterInTurretRangeTest(){
        Game gameTest = new Game();
        Balance bl = new Balance();
        boolean canPlaceTurret=false;
        int x=0;
        int y=0;
        for (int i =0;i<gameTest.getMap().getSize();i++){
            for (int j =0;j<gameTest.getMap().getSize();j++){
                if (gameTest.getMap().getMapAttribute(i, j).getAttribute().equals("land")){
                    x=i;
                    y=j;
                    canPlaceTurret=true;
                }
            }
        }
        if (!canPlaceTurret){
            return;
        }
        gameTest.buyTurret(x,y,UnitTypes.SNIPER);
        gameTest.buyFighter(UnitTypes.BOMBER);
        gameTest.getPlayerOne().getFighters().get(0).setPosition(x, y);

        assertTrue(  gameTest.fighterTakesDamage(gameTest.getActivePlayer().getFighters().get(0),2));
    }

    //nincs benne hanem kintebb van
    @Test
    public void isFighterInTurretRangeTest2(){
        Game gameTest = new Game();
        Balance bl = new Balance();
        boolean canPlaceTurret=false;
        int x=0;
        int y=0;
        for (int i =0;i<gameTest.getMap().getSize();i++){
            for (int j =0;j<gameTest.getMap().getSize();j++){
                if (gameTest.getMap().getMapAttribute(i, j).getAttribute().equals("land")){
                    x=i;
                    y=j;
                    canPlaceTurret=true;
                }
            }
        }
        if (!canPlaceTurret){
            return;
        }
        gameTest.buyTurret(x,y,UnitTypes.SNIPER);
        gameTest.buyFighter(UnitTypes.BOMBER);
        if (x+bl.getSniperRange()+1 <gameTest.getMap().getSize()) {
            gameTest.getPlayerOne().getFighters().get(0).setPosition(x + bl.getSniperRange() + 1, y);
        }
        else if (x-bl.getSniperRange()+1 >0) {
            gameTest.getPlayerOne().getFighters().get(0).setPosition(x - bl.getSniperRange() - 1, y);
        }

        assertFalse(gameTest.fighterTakesDamage(gameTest.getActivePlayer().getFighters().get(0),2));
    }


    @Test
    public void fighterTakesDamageTest(){
        Game gameTest = new Game();
        Balance bl = new Balance();
        boolean canPlaceTurret=false;
        int x=0;
        int y=0;
        for (int i =0;i<gameTest.getMap().getSize();i++){
            for (int j =0;j<gameTest.getMap().getSize();j++){
                if (gameTest.getMap().getMapAttribute(i, j).getAttribute().equals("land")){
                    x=i;
                    y=j;
                    canPlaceTurret=true;
                }
            }
        }
        if (!canPlaceTurret){
            return;
        }

        gameTest.buyTurret(x,y,UnitTypes.SNIPER);

        gameTest.buyFighter(UnitTypes.BOMBER);
        gameTest.getPlayerOne().getFighters().get(0).setPosition(x, y);

        assertTrue(  gameTest.fighterTakesDamage(gameTest.getActivePlayer().getFighters().get(0),2));

    }

    //nem tudja megvenni
    @Test
    public void canPlayerBuyThisTest(){
        Game gameTest = new Game();
        Balance balanceTest = new Balance();
        gameTest.getActivePlayer().setGold(balanceTest.getSniperCost()-1);

        assertFalse(gameTest.canPlayerBuyThis(gameTest.getActivePlayer(), UnitTypes.SNIPER));
    }

    @Test
    public void canPlayerBuyThisTest2(){
        Game gameTest = new Game();
        Balance balanceTest = new Balance();
        gameTest.getActivePlayer().setGold(balanceTest.getSniperCost());

        assertTrue(gameTest.canPlayerBuyThis(gameTest.getActivePlayer(), UnitTypes.SNIPER));
    }

    @Test
    public void damageCastleTest1(){
        Game gameTest = new Game();
        Balance balanceTest = new Balance();
        int castleHP = balanceTest.getCastleHP();
        gameTest.damageCastle(10, 1);

        assertEquals(castleHP-10, parseInt( gameTest.getActivePlayer().getCastleHP()));
    }

    @Test
    public void damageCastleTest2(){
        Game gameTest = new Game();
        Balance balanceTest = new Balance();
        int castleHP = balanceTest.getCastleHP();
        gameTest.damageCastle(castleHP, 1);

        assertEquals(0, parseInt( gameTest.getActivePlayer().getCastleHP()));
    }

    @Test
    public void damageCastleTest3(){
        Game gameTest = new Game();
        Balance balanceTest = new Balance();
        int castleHP = balanceTest.getCastleHP();
        gameTest.damageCastle(castleHP+1, 1);

        assertTrue(gameTest.isPlayerTwoTheWinner());

    }


    @Test
    public void decideIfTurretPlacableTest(){
        Game gameTest = new Game();
        boolean canPlaceTurret=false;
        int x=0;
        int y=0;
        for (int i =0;i<gameTest.getMap().getSize();i++){
            for (int j =0;j<gameTest.getMap().getSize();j++){
                if (gameTest.getMap().getMapAttribute(i, j).getAttribute().equals("land") && !gameTest.getMap().getMapAttribute(i, j).isNextToCastle()){
                    x=i;
                    y=j;
                    canPlaceTurret=true;
                }
            }
        }
        if (!canPlaceTurret){
            return;
        }

        assertEquals(1,gameTest.decideIfTurretPlacable(x,y));
    }

    //nem lehet lerakni mert a castle mellett van
    @Test
    public void decideIfTurretPlacableTest2(){
        Game gameTest = new Game();
        boolean canPlaceTurret=false;
        int x=0;
        int y=0;
        for (int i =0;i<gameTest.getMap().getSize();i++){
            for (int j =0;j<gameTest.getMap().getSize();j++){
                if (gameTest.getMap().getMapAttribute(i, j).getAttribute().equals("land") && gameTest.getMap().getMapAttribute(i, j).isNextToCastle()){
                    x=i;
                    y=j;
                    canPlaceTurret=true;
                }
            }
        }
        if (!canPlaceTurret){
            return;
        }

        assertEquals(0,gameTest.decideIfTurretPlacable(x,y));
    }


    //nem landra történő lerakás
    @Test
    public void decideIfTurretPlacableTest3(){
        Game gameTest = new Game();
        boolean canPlaceTurret=false;
        int x=0;
        int y=0;
        for (int i =0;i<gameTest.getMap().getSize();i++){
            for (int j =0;j<gameTest.getMap().getSize();j++){
                if (!gameTest.getMap().getMapAttribute(i, j).getAttribute().equals("land") ){
                    x=i;
                    y=j;
                    canPlaceTurret=true;
                }
            }
        }
        if (!canPlaceTurret){
            return;
        }

        assertEquals(0,gameTest.decideIfTurretPlacable(x,y));
    }

    @Test
    public void findSelectedUnitTest(){
        Game gameTest = new Game();
        gameTest.getActivePlayer().buyFighter(new Soldier());
        Units test =gameTest.findSelectedUnit(UnitTypes.SOLDIER);
        assertEquals("soldier", test.getAttribute());
    }

    @Test
    public void findSelectedUnitTest2(){
        Game gameTest = new Game();
        gameTest.getActivePlayer().buyFighter(new Soldier());
        Units test =gameTest.findSelectedUnit(UnitTypes.ATTRIBUTE);
        assertNull(test);
    }

    @Test
    public void buyTurretTest(){
        Game gameTest = new Game();
        int x=2;
        int y=2;
        outerloop:
        for (int i =0;i<gameTest.getMap().getSize();i++){
            for (int j =0;j<gameTest.getMap().getSize();j++){
                if (gameTest.getMap().getMapAttribute(i, j).getAttribute().equals("land") && gameTest.getMap().getMapAttribute(i, j).isNextToCastle()){
                    x=i;
                    y=j;
                    break outerloop;
                }
            }
        }
        gameTest.buyTurret(x,y, UnitTypes.SNIPER);

        assertEquals(gameTest.getActivePlayer().getTowers().get(0).getUnitTypes(), UnitTypes.SNIPER);
    }

    @Test
    public void buyTurretTest2(){
        Game gameTest = new Game();
        int x=2;
        int y=2;
        outerloop:
        for (int i =0;i<gameTest.getMap().getSize();i++){
            for (int j =0;j<gameTest.getMap().getSize();j++){
                if (gameTest.getMap().getMapAttribute(i, j).getAttribute().equals("mountain") ){
                    x=i;
                    y=j;
                    break outerloop;
                }
            }
        }
        gameTest.buyTurret(x,y, UnitTypes.SNIPER);
        assertEquals(gameTest.getActivePlayer().getTowers().size(), 1);
    }

    @Test
    public void MapTest1(){
        Game gameTest = new Game();
        int x=0;
        int y=0;
        outerloop:
        for (int i =0;i<gameTest.getMap().getSize();i++){
            for (int j =0;j<gameTest.getMap().getSize();j++){
                if (gameTest.getMap().getMapAttribute(i, j).getMapAttribute().equals(MapAttributesTypes.CASTLE1)){
                    x=i;
                    y=j;
                    break outerloop;
                }
            }
        }
        assertEquals(gameTest.getMap().getMapAttribute(x-1, y-1).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x-1, y).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x-1, y+1).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x, y-1).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x, y+1).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x+1, y-1).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x+1, y).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x+1, y+1).getMapAttribute(), MapAttributesTypes.LAND);
    }

    @Test
    public void MapTest2(){
        Game gameTest = new Game();
        int x=0;
        int y=0;
        outerloop:
        for (int i =0;i<gameTest.getMap().getSize();i++){
            for (int j =0;j<gameTest.getMap().getSize();j++){
                if (gameTest.getMap().getMapAttribute(i, j).getMapAttribute().equals(MapAttributesTypes.CASTLE2)){
                    x=i;
                    y=j;
                    break outerloop;
                }
            }
        }
        assertEquals(gameTest.getMap().getMapAttribute(x-1, y-1).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x-1, y).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x-1, y+1).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x, y-1).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x, y+1).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x+1, y-1).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x+1, y).getMapAttribute(), MapAttributesTypes.LAND);
        assertEquals(gameTest.getMap().getMapAttribute(x+1, y+1).getMapAttribute(), MapAttributesTypes.LAND);
    }



}