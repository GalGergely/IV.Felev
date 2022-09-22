import org.junit.Test;
import ourstd.model.map.*;
import ourstd.model.*;
import ourstd.model.units.*;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.*;

public class PathfindTest {

    @Test
    public void TheShortestPathIsAlwaysStraight(){
        Map map = new Map(10, true);
        Soldier soldier = new Soldier();
        Pathfinder pf = new Pathfinder(map, soldier,1);
        Node[][] nodeMap = pf.getNodeMap();
        assertEquals(0,nodeMap[0][0].getType());
        assertEquals(5,nodeMap[1][1].getType());
        assertEquals(5,nodeMap[2][2].getType());
        assertEquals(5,nodeMap[3][3].getType());
        assertEquals(5,nodeMap[7][7].getType());
        assertEquals(5,nodeMap[8][8].getType());
        assertEquals(1,nodeMap[9][9].getType());
    }
    @Test
    public void ShortestPathTest1(){
        Map map = new Map(10, true);
        ArrayList<MapAttributes> temperary;

        Mountain mountain = new Mountain();
        int row = 2;
        int col = 0;
        temperary = map.getGameBoard().get(row);
        temperary.set(col, mountain);
        map.getGameBoard().set(row, temperary);

        row = 2;
        col = 1;
        temperary = map.getGameBoard().get(row);
        temperary.set(col, mountain);
        map.getGameBoard().set(row, temperary);

        row = 2;
        col = 2;
        temperary = map.getGameBoard().get(row);
        temperary.set(col, mountain);
        map.getGameBoard().set(row, temperary);

        row = 2;
        col = 3;
        temperary = map.getGameBoard().get(row);
        temperary.set(col, mountain);
        map.getGameBoard().set(row, temperary);

        row = 2;
        col = 4;
        temperary = map.getGameBoard().get(row);
        temperary.set(col, mountain);
        map.getGameBoard().set(row, temperary);

        Soldier soldier = new Soldier();
        Pathfinder pf = new Pathfinder(map, soldier,1);
        Node[][] nodeMap = pf.getNodeMap();
        assertEquals(0,nodeMap[0][0].getType());
        assertEquals(5,nodeMap[1][1].getType());
        assertEquals(5,nodeMap[1][2].getType());
        assertEquals(5,nodeMap[1][3].getType());
        assertEquals(5,nodeMap[1][4].getType());
        assertEquals(5,nodeMap[2][5].getType());
        assertEquals(5,nodeMap[3][6].getType());
        assertEquals(5,nodeMap[5][8].getType());
        assertEquals(5,nodeMap[6][9].getType());
        assertEquals(5,nodeMap[7][9].getType());
        assertEquals(5,nodeMap[8][9].getType());
        assertEquals(1,nodeMap[9][9].getType());
    }

    @Test
    public void NoPathTest(){
        Map map = new Map(10, true);
        ArrayList<MapAttributes> temperary;

        Mountain mountain = new Mountain();
        int row = 2;
        int col = 0;
        temperary = map.getGameBoard().get(row);
        temperary.set(col, mountain);
        map.getGameBoard().set(row, temperary);

        row = 2;
        col = 1;
        temperary = map.getGameBoard().get(row);
        temperary.set(col, mountain);
        map.getGameBoard().set(row, temperary);

        row = 2;
        col = 2;
        temperary = map.getGameBoard().get(row);
        temperary.set(col, mountain);
        map.getGameBoard().set(row, temperary);

        row = 1;
        col = 2;
        temperary = map.getGameBoard().get(row);
        temperary.set(col, mountain);
        map.getGameBoard().set(row, temperary);

        row = 0;
        col = 2;
        temperary = map.getGameBoard().get(row);
        temperary.set(col, mountain);
        map.getGameBoard().set(row, temperary);

        Soldier soldier = new Soldier();
        Pathfinder pf = new Pathfinder(map, soldier,1);
        Node[][] nodeMap = pf.getNodeMap();
        for(int i=0; i<map.getSize(); i++){
            for(int j=0; j<map.getSize(); j++) {
                assertNotEquals(5,nodeMap[i][j].getType());
            }
        }
    }

}
