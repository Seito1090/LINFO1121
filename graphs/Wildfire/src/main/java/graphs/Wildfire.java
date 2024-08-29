package graphs;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Let's consider a forest represented as a 2D grid.
 * Each cell of the grid can be in one of three states:
 *
 * 0 representing an empty spot.
 * 1 representing a tree.
 * 2 representing a burning tree (indicating a wildfire).
 *
 * The fire spreads from a burning tree to its four neighboring cells (up, down, left, and right) if there's a tree there.
 * Each minute, the trees in the neighboring cells of burning tree catch on fire.
 *
 * Your task is to calculate how many minutes it would take for the fire to spread throughout the forest i.e. to burn all the trees.
 *
 * If there are trees that cannot be reached by the fire (for example, isolated trees with no adjacent burning trees),
 * we consider that the fire will never reach them and -1 is returned.
 *
 * The time-complexity of your algorithm must be O(n) with n the number of cells in the forest.
 */
public class Wildfire {

    static final int EMPTY = 0;
    static final int TREE = 1;
    static final int BURNING = 2;


    class Blob {
        int x;
        int y; 
        int time;

        Blob(int x, int y, int time){
            this.x = x;
            this.y = y;
            this.time = time;
        }
        
    }

    /**
     * This method calculates how many minutes it would take for the fire to spread throughout the forest.
     *
     * @param forest
     * @return the number of minutes it would take for the fire to spread throughout the forest,
     *         -1 if the forest cannot be completely burned.
     */

    /* General idea : create a queue / list where you throw each new burning tree and when itterating you just pop them back out and so on */
    public int burnForest(int [][] forest) {
        if (forest.length == 0 || forest[0].length == 0) return -1; /* you passed the wrong thing into the function */

        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}}; /* every possible direction */

        Queue<Blob> blobs = new LinkedList<>(); /* every blob to be checked */
        
        for (int posX = 0; posX < forest.length; posX++){ /* Initial blobs from which the fire spreads */
            for (int posY = 0; posY < forest[0].length; posY++){
                if (forest[posX][posY] == BURNING) blobs.add(new Blob(posX, posY, 0));
            }
        }

        int timeToBrun = 0;

        /* FIREEE */
        while (!blobs.isEmpty()){
            Blob current = blobs.poll();

            /* We now find ourselves with a burning tree at this point, sd we SPREEEEEEAD IT */
            for (int[] direction : directions){
                if (current.x  + direction[0] >= 0 && current.y + direction[1] >= 0 && current.x + direction[0] < 
                forest.length && current.y + direction[1] < forest[0].length) {
                    if (forest[current.x + direction[0]][current.y+direction[1]] == TREE){
                        forest[current.x + direction[0]][current.y+direction[1]] = BURNING;
                        blobs.add(new Blob(current.x + direction[0], current.y+direction[1], current.time+1));
                        timeToBrun = current.time > timeToBrun ? current.time: timeToBrun;
                    }
                }
            }
        }

        for (int[] forestBranch : forest){ /* Check if our work was done perfectly, as all things should be */
            for (int tree : forestBranch){
                if (tree == TREE) return -1;
            }
        }

        return timeToBrun+1;
    }
}