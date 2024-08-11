package sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,1,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Each entry in the matrix represents an altitude point at the corresponding grid coordinate.
 * The goal is to implement a GlobalWarmingImpl class that extends the GlobalWarming abstract class described below.
 *
 * Given a global water level, all positions in the matrix with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3, all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4), 1 ,(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is nbSafePoints
 * - calculating the number of safe points for a given water level
 *
 * Complete the code below. Pay attention to the expected time complexity of each method.
 * To meet this time complexity, you need to do some pre-computation in the constructor.
 * Feel free to create internal classes in GlobalWarmingImpl to make your implementation easier.
 * Feel free to use any method or data structure available in the Java language and API.
 */

abstract class GlobalWarming {


    protected final int[][] altitude;

    /**
     * @param altitude is a n x n matrix of int values representing altitudes (positive or negative)
     */
    public GlobalWarming(int[][] altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @param waterLevel
     * @return the number of entries in altitude matrix that would be above
     *         the specified waterLevel.
     *         Warning: this is not the waterLevel given in the constructor/
     */
    public abstract int nbSafePoints(int waterLevel);

}


public class GlobalWarmingImpl extends GlobalWarming {

    private Integer[] fatArray;

    public GlobalWarmingImpl(int[][] altitude) {
        super(altitude);
        int x,y;
        // expected pre-processing time in the constructror : O(n^2 log(n^2))
        fatArray = new Integer[altitude.length*altitude[0].length];
        for (int a = 0; a < altitude[0].length * altitude.length; a++){
            x = a / altitude.length;
            y = a % altitude[0].length;
            fatArray[a] = altitude[x][y];
        }
        myQuickSort();
    }

    /**
     * Returns the number of safe points given a water level
     *
     * @param waterLevel the level of water
     */
    public int nbSafePoints(int waterLevel) {
        // TODO
        // expected time complexity O(log(n^2))
        int idx = find(waterLevel);
        return fatArray.length - idx;
    }

    private int find(int cutoff){
        //We do a binary search, halfing each time to get to the answer quicker 
        int lo = 0, hi = fatArray.length - 1, idx = 0;
        // Case where everything is flooded 
        if (fatArray[hi] <= cutoff) return fatArray.length;
        // Case where everything is not flooded 
        if (fatArray[lo] > cutoff) return 0;
        
        // Default case 
        while (true) {
            // itterate 
            idx = lo + (hi - lo) / 2;
            if (fatArray[idx] <= cutoff) lo = idx + 1;
            else {
                if (idx == 0 || fatArray[idx-1] <= cutoff) return idx;
                hi = idx - 1;
            }
        }
    }


    private void myQuickSort(){
        Collections.shuffle(Arrays.asList(fatArray));
        sort(0, fatArray.length - 1);
    }
    
    private void sort(int lo, int hi){
        if (hi <= lo) return;
        int mid = partition(lo, hi);
        sort(lo, mid-1);
        sort(mid+1, hi);
    }

    private int partition(int lo, int hi){
        int a = lo, b = hi+1;
        int item = fatArray[a];
        while (true) {
            while (fatArray[++a] < item) if (a == hi) break;
            while (item < fatArray[--b]) if (b == lo) break;
            if (a >= b) break;
            exch(a,b);
        }
        exch(lo, b);
        return b;
    }

    private void exch(int idx0, int idx1){
        Integer temp = fatArray[idx0];
        fatArray[idx0] = fatArray[idx1];
        fatArray[idx1] = temp;
    }
}
