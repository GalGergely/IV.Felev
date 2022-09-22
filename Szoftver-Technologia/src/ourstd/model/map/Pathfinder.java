package ourstd.model.map;

import ourstd.model.Position;
import ourstd.model.units.Fighters;
import ourstd.model.units.UnitTypes;

import java.util.ArrayList;

public class Pathfinder {
    private final Map map;
    private final Node[][] nodeMap;
    private Position finish;
    private boolean solving = true;
    private final Fighters fighter;

    /**
     * Megmutatja az utvonalat amin a harcos haladni tud
     * @param map palya
     * @param fighter az adott harcos
     * @param playerNumber melyik jatekose a harcos
     */
    public Pathfinder(Map map, Fighters fighter, int playerNumber) {
        this.fighter = fighter;
        this.map = map;

        nodeMap = new Node[map.getSize()][map.getSize()];
        if (fighter.getUnitTypes().equals(UnitTypes.CLIMBER)) {
            for (int i = 0; i < map.getSize(); i++) {
                for (int j = 0; j < map.getSize(); j++) {
                    if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.CASTLE1)) {
                        if (playerNumber == 2) {
                            nodeMap[i][j] = new Node(1, i, j);
                            finish = new Position(i, j);
                        } else {
                            nodeMap[i][j] = new Node(3, i, j);
                            finish = new Position(i, j);
                        }
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.CASTLE2)) {
                        if (playerNumber == 1) {
                            nodeMap[i][j] = new Node(1, i, j);
                            finish = new Position(i, j);
                        } else {
                            nodeMap[i][j] = new Node(3, i, j);
                            finish = new Position(i, j);
                        }
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.MOUNTAIN)) {
                        nodeMap[i][j] = new Node(3, i, j);
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.WATER)) {
                        nodeMap[i][j] = new Node(2, i, j);
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.LAND)) {
                        if (!map.getGameBoard().get(i).get(j).isAvailable()) {
                            nodeMap[i][j] = new Node(2, i, j);
                        } else {
                            nodeMap[i][j] = new Node(3, i, j);
                        }

                    } else {
                        nodeMap[i][j] = new Node(3, i, j);
                    }
                }
            }
        } else if (fighter.getUnitTypes().equals(UnitTypes.SWIMMER)) {
            for (int i = 0; i < map.getSize(); i++) {
                for (int j = 0; j < map.getSize(); j++) {
                    if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.CASTLE1)) {
                        if (playerNumber == 2) {
                            nodeMap[i][j] = new Node(1, i, j);
                            finish = new Position(i, j);
                        } else {
                            nodeMap[i][j] = new Node(3, i, j);
                            finish = new Position(i, j);
                        }
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.CASTLE2)) {
                        if (playerNumber == 1) {
                            nodeMap[i][j] = new Node(1, i, j);
                            finish = new Position(i, j);
                        } else {
                            nodeMap[i][j] = new Node(3, i, j);
                            finish = new Position(i, j);
                        }
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.MOUNTAIN)) {
                        nodeMap[i][j] = new Node(2, i, j);
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.WATER)) {
                        nodeMap[i][j] = new Node(3, i, j);
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.LAND)) {
                        if (!map.getGameBoard().get(i).get(j).isAvailable()) {
                            nodeMap[i][j] = new Node(2, i, j);
                        } else {
                            nodeMap[i][j] = new Node(3, i, j);
                        }

                    } else {
                        nodeMap[i][j] = new Node(3, i, j);
                    }
                }
            }
        } else if (fighter.getUnitTypes().equals(UnitTypes.BOMBER)) {
            for (int i = 0; i < map.getSize(); i++) {
                for (int j = 0; j < map.getSize(); j++) {
                    if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.CASTLE1)) {
                        if (playerNumber == 2) {
                            nodeMap[i][j] = new Node(1, i, j);
                            finish = new Position(i, j);
                        } else {
                            nodeMap[i][j] = new Node(3, i, j);
                            finish = new Position(i, j);
                        }
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.CASTLE2)) {
                        if (playerNumber == 1) {
                            nodeMap[i][j] = new Node(1, i, j);
                            finish = new Position(i, j);
                        } else {
                            nodeMap[i][j] = new Node(3, i, j);
                            finish = new Position(i, j);
                        }
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.MOUNTAIN)) {
                        nodeMap[i][j] = new Node(2, i, j);
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.WATER)) {
                        nodeMap[i][j] = new Node(2, i, j);
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.LAND)) {
                        if (!map.getGameBoard().get(i).get(j).isAvailable()) {
                            nodeMap[i][j] = new Node(1, i, j);
                        } else {
                            nodeMap[i][j] = new Node(3, i, j);
                        }
                    } else {
                        nodeMap[i][j] = new Node(3, i, j);
                    }
                }
            }
        } else {
            for (int i = 0; i < map.getSize(); i++) {
                for (int j = 0; j < map.getSize(); j++) {
                    if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.CASTLE1)) {
                        if (playerNumber == 2) {
                            nodeMap[i][j] = new Node(1, i, j);
                            finish = new Position(i, j);
                        } else {
                            nodeMap[i][j] = new Node(3, i, j);
                            finish = new Position(i, j);
                        }
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.CASTLE2)) {
                        if (playerNumber == 1) {
                            nodeMap[i][j] = new Node(1, i, j);
                            finish = new Position(i, j);
                        } else {
                            nodeMap[i][j] = new Node(3, i, j);
                            finish = new Position(i, j);
                        }
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.MOUNTAIN)) {
                        nodeMap[i][j] = new Node(2, i, j);
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.WATER)) {
                        nodeMap[i][j] = new Node(2, i, j);
                    } else if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.LAND)) {
                        if (!map.getGameBoard().get(i).get(j).isAvailable()) {
                            nodeMap[i][j] = new Node(2, i, j);
                        } else {
                            nodeMap[i][j] = new Node(3, i, j);
                        }

                    } else {
                        nodeMap[i][j] = new Node(3, i, j);
                    }
                }
            }
        }
        nodeMap[fighter.getPosition().getX()][fighter.getPosition().getY()] = new Node(0, fighter.getPosition().getX(), fighter.getPosition().getY());
        startSearch();

    }

    public Node[][] getNodeMap() {
        return nodeMap;
    }

    /**
     * Elinditja a keresest
     */
    public void startSearch() {
        if (solving) {
            aStar();
        }
    }

    public void logNodeMap() {
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                System.out.print(nodeMap[i][j] + " ");
            }
            System.out.println();
        }
    }


    /**
     * kiemelve az algoritmus, kicserelheto masra.
     */
    public void aStar() {
        ArrayList<Node> priority = new ArrayList<Node>();
        priority.add(nodeMap[fighter.getPosition().getX()][fighter.getPosition().getY()]);
        while (solving) {
            if (priority.size() <= 0) {
                solving = false;
                break;
            }
            int hops = priority.get(0).getHops() + 1;
            ArrayList<Node> explored = exploreNeighbors(priority.get(0), hops);
            if (explored.size() > 0) {
                priority.remove(0);
                priority.addAll(explored);
            } else {
                priority.remove(0);
            }
            sortQue(priority);
        }
    }

    public ArrayList<Node> sortQue(ArrayList<Node> sort) {
        int c = 0;
        while (c < sort.size()) {
            int sm = c;
            for (int i = c + 1; i < sort.size(); i++) {
                if (sort.get(i).getEuclidDist(finish) + sort.get(i).getHops() < sort.get(sm).getEuclidDist(finish) + sort.get(sm).getHops())
                    sm = i;
            }
            if (c != sm) {
                Node temp = sort.get(c);
                sort.set(c, sort.get(sm));
                sort.set(sm, temp);
            }
            c++;
        }
        return sort;
    }

    /**
     * Megnezi a szomszedokat,
     * @param current mostani allapot
     * @param hops lepesek
     * @return felfedetzett e
     */
    public ArrayList<Node> exploreNeighbors(Node current, int hops) {    //EXPLORE NEIGHBORS
        ArrayList<Node> explored = new ArrayList<Node>();    //LIST OF NODES THAT HAVE BEEN EXPLORED
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                int xbound = current.getX() + a;
                int ybound = current.getY() + b;
                if ((xbound > -1 && xbound < map.getSize()) && (ybound > -1 && ybound < map.getSize())) {    //MAKES SURE THE NODE IS NOT OUTSIDE THE GRID
                    Node neighbor = nodeMap[xbound][ybound];
                    if ((neighbor.getHops() == -1 || neighbor.getHops() > hops) && neighbor.getType() != 2) {    //CHECKS IF THE NODE IS NOT A WALL AND THAT IT HAS NOT BEEN EXPLORED
                        explore(neighbor, current.getX(), current.getY(), hops);    //EXPLORE THE NODE
                        explored.add(neighbor);    //ADD THE NODE TO THE LIST
                    }
                }
            }
        }
        return explored;
    }

    public void explore(Node current, int lastx, int lasty, int hops) {    //EXPLORE A NODE
        if (current.getType() != 0 && current.getType() != 1)    //CHECK THAT THE NODE IS NOT THE START OR FINISH
            current.setType(4);    //SET IT TO EXPLORED
        current.setLastNode(lastx, lasty);    //KEEP TRACK OF THE NODE THAT THIS NODE IS EXPLORED FROM
        current.setHops(hops);    //SET THE HOPS FROM THE START
        if (current.getType() == 1) {    //IF THE NODE IS THE FINISH THEN BACKTRACK TO GET THE PATH
            backtrack(current.getLastX(), current.getLastY(), hops);
        }
    }

    public void backtrack(int lx, int ly, int hops) {    //BACKTRACK
        while (hops > 0) {    //BACKTRACK FROM THE END OF THE PATH TO THE START
            Node current = nodeMap[lx][ly];
            current.setType(5);
            lx = current.getLastX();
            ly = current.getLastY();
            hops--;
        }
        solving = false;
    }

    public Fighters getFighter() {
        return fighter;
    }
}