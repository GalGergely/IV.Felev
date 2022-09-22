package ourstd.model.map;

import ourstd.model.Balance;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Map {

    Balance bl = new Balance();
    int rangeForCastles = bl.getRangeForCastles();
    private final ArrayList<ArrayList<MapAttributes>> gameBoard;

    private Castle castle1;
    private Castle castle2;

    /** foleg teszteleshez hasznalt palya, uresre generalodik.
     * @param n meret
     * @param asd tesztelesi attributum
     */
     public Map(int n, boolean asd) {
      gameBoard = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<MapAttributes> tmp = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                 tmp.add(new Land());
            }
            gameBoard.add(tmp);
        }
        //generate castle 1
        ArrayList <MapAttributes> temporary = gameBoard.get(0);
        Castle castle1 = new Castle(1);
        temporary.set(0, castle1);
        gameBoard.set(0, temporary);

        //mountain
        //temporary = gameBoard.get(0+1);
        //Mountain mountain = new Mountain();
        //temporary.set(0+1, mountain);
        //gameBoard.set(0+1, temporary);
        //
        ////water
        //temporary = gameBoard.get(0+2);
        //Water water = new Water();
        //temporary.set(0+2, water);
        //gameBoard.set(0+2, temporary);
        
        //generate castle 2
        temporary = gameBoard.get(n-1);
        Castle castle2 = new Castle(2);
        temporary.set(n-1, castle2);
        gameBoard.set(n-1, temporary);
     }

    /** Ez egy random szabalyos palyat general.
     * @param n map merete
     */
    public Map(int n) {
        gameBoard = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<MapAttributes> tmp = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                int tileRandom = random(0, 10);

                if (tileRandom == 8) {
                    Mountain mountain = new Mountain();
                    tmp.add(mountain);
                }
                if (tileRandom >= 9) {
                    Water water = new Water();
                    tmp.add(water);
                } else {
                    Land land = new Land();
                    tmp.add(land);
                }
            }
            gameBoard.add(tmp);
        }

        int x1 = random(1, rangeForCastles);
        int y1 = random(1, rangeForCastles);
        int x2 = random(n - rangeForCastles, n - 1);
        int y2 = random(n - rangeForCastles, n - 1);

        //generate castle 1
        ArrayList<MapAttributes> temporary = gameBoard.get(x1);
        castle1 = new Castle(1);
        temporary.set(y1, castle1);
        gameBoard.set(x1, temporary);

        //generate castle 2
        temporary = gameBoard.get(x2);
        castle2 = new Castle(2);
        temporary.set(y2, castle2);
        gameBoard.set(x2, temporary);

        deleteAroundCastle(x1, y1);
        deleteAroundCastle(x2, y2);
    }

    /** Ez general egy filebol palyat.
     * @param n map merete
     * @param filename file amibol palyat general
     */
    public Map(int n,String filename){
        gameBoard = new ArrayList<>();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                ArrayList<MapAttributes> tmp = new ArrayList<>();
                String data = myReader.nextLine();
                String[] oneLine = data.split("");
                String word = "";
                int i = 0;
                while(i < oneLine.length) {
                    String character = oneLine[i];
                    word += character;
                    if (word.equals("land")){
                        tmp.add(new Land());
                        word = "";
                    }
                    else if (word.equals("mountain")){
                        tmp.add(new Mountain());
                        word = "";
                    }
                    else if (word.equals("castle1")){
                        castle1 =new Castle(1);
                        tmp.add(castle1);
                        word = "";
                    }
                    else if (word.equals("castle2")){
                        castle2 = new Castle(2);
                        tmp.add(castle2);
                        word = "";
                    }
                    else if (word.equals("water")){
                        tmp.add(new Water());
                        word = "";
                    }
                    i++;
                }
                gameBoard.add(tmp);
               // System.out.println(data);

            }
            myReader.close();
            //gameboardConsoleLog();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /** A teruleteket landre allitja a castle korul
     * @param x x
     * @param y y
     */
    void deleteAroundCastle(int x, int y) {
        ArrayList<MapAttributes> temporary;
        temporary = gameBoard.get(x);
        temporary.set(y - 1, new Land(true));
        temporary.set(y + 1, new Land(true));
        gameBoard.set(x, temporary);
        //alatta levo teruletek
        temporary = gameBoard.get(x - 1);
        temporary.set(y, new Land(true));
        temporary.set(y - 1, new Land(true));
        temporary.set(y + 1, new Land(true));
        gameBoard.set(x - 1, temporary);
        //felette levo teruletek
        temporary = gameBoard.get(x + 1);
        temporary.set(y, new Land(true));
        temporary.set(y - 1, new Land(true));
        temporary.set(y + 1, new Land(true));
        gameBoard.set(x + 1, temporary);
    }

    /** Egy random szamot ad vissza a ket ertek kozott
     * @param low legkisebb
     * @param high legnagyobb
     * @return ranmdom szam
     */
    int random(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    /**
     * Consolrera irja a palyat
     */
    public void logMap() {
        for (int i = 0; i < gameBoard.size(); i++) {
            for (int j = 0; j < gameBoard.size(); j++) {
                gameBoard.get(i).get(j).consoleLog();
            }
        }
    }

    public List<ArrayList<MapAttributes>> getMap() {
        return this.gameBoard;
    }


    public void setMapAttribute(int x, int y, MapAttributes attribute) {
        gameBoard.get(x).set(y, attribute);
    }
    public MapAttributes getMapAttribute(int x, int y) {return gameBoard.get(x).get(y);}

    public int getSize() {
        return this.gameBoard.size();
    }

    public List<ArrayList<MapAttributes>> getGameBoard() {
        return gameBoard;
    }

    public Castle getCastleForPlayer(int whichPlayer){
        if(whichPlayer == 1){
            return this.castle1;
        } else {
            return this.castle2;
        }
    }


}