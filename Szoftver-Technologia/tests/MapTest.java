import org.junit.Test;
import ourstd.model.map.*;

import static org.junit.Assert.*;
import static ourstd.model.map.MapAttributesTypes.CASTLE1;
import static ourstd.model.map.MapAttributesTypes.CASTLE2;

public class MapTest {

    @Test
    public void areCastlesPlaced(){
        Map map = new Map(10);
        boolean castle1Available = false;
        boolean castle2Available = false;
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                System.out.println(map.getGameBoard().get(i).get(j).getMapAttribute());
                if(map.getGameBoard().get(i).get(j).getMapAttribute().equals(CASTLE1)) {
                    castle1Available = true;
                } else if(map.getGameBoard().get(i).get(j).getMapAttribute().equals(CASTLE2)) {
                    castle2Available = true;
                }
            }
        }
        assertTrue(castle1Available);
        assertTrue(castle2Available);
    }

    @Test
    public void areCastlesNotInPirimiter(){
        Map map = new Map(10);
        boolean castle1Available = false;
        boolean castle2Available = false;
        for(int i = 0; i < 10; i++) {
            if(map.getGameBoard().get(i).get(0).getMapAttribute().equals(CASTLE1)) {
                castle1Available =  true;
            }
            if(map.getGameBoard().get(i).get(0).getMapAttribute().equals(CASTLE2)) {
                castle2Available =  true;
            }
            if(map.getGameBoard().get(i).get(9).getMapAttribute().equals(CASTLE1)) {
                castle1Available =  true;
            }
            if(map.getGameBoard().get(i).get(9).getMapAttribute().equals(CASTLE2)) {
                castle2Available =  true;
            }
            if(map.getGameBoard().get(9).get(i).getMapAttribute().equals(CASTLE1)) {
                castle1Available =  true;
            }
            if(map.getGameBoard().get(9).get(i).getMapAttribute().equals(CASTLE2)) {
                castle2Available =  true;
            }
            if(map.getGameBoard().get(0).get(i).getMapAttribute().equals(CASTLE1)) {
                castle1Available =  true;
            }
            if(map.getGameBoard().get(0).get(i).getMapAttribute().equals(CASTLE2)) {
                castle2Available =  true;
            }
        }
        assertFalse(castle2Available);
        assertFalse(castle1Available);
    }
}
