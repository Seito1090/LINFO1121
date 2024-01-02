package sorting;

import java.util.Random;

/**
 * You're a photographer for a soccer meet.
 * You will be taking pictures of pairs of opposing teams.
 * All teams have the same number of players.
 * A team photo consists of a front row of players and
 * a back row of players.
 * In order to be visible, a player in the back row must be taller (not equal thus)
 * than the player in front of him.
 * All players in a row must be from the same team.
 * 
 * You must design an algorithm that takes as input two teams (their heights) and checks if it is
 * possible to arrange them to take a photo given the constraints.
 * If so, your method must return the
 * sum of the difference between the height of the aligned players.
 * For example if each team has three players, and their respective heights are [170, 160, 180] and
 * [192, 175, 178], then your method must return 35.
 * If no arrangement can be made, it returns -1.
 *
 * Feel free to use existing java classes.
 */
public class Photo {

    public static void exch(int[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void shuffleIntArray(int[] array) {
        Random rand = new Random();

        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);

            // Swap array[i] and array[index]
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }

    public static int partition(int[] array, int lo, int hi){
        int a = lo, b = hi+1;
        int c = array[a];
        while (true){
            while(array[++a] < c) if (a == hi) break;
            while(array[--b] > c) if (b == lo) break;
            if (a >= b) break;
            exch(array, a, b);
        }
        exch(array, lo, b);
        return b;
    }

    public static void sort(int[] array, int lo, int hi){
        //check if singleton
        if (hi <= lo) return;
        int j = partition(array, lo, hi);
        sort(array, lo, j-1);
        sort(array, j+1, hi);
    }

    public static void sortArray(int [] array){
        shuffleIntArray(array);
        sort(array, 0, array.length - 1);
    }




    /**
     * This method checks if there is an arrangement of team A and B such that
     * a photo can be taken. If this is the case, it returns the sum of the
     * (absolute) difference between the players placed on the same spot (one
     * behind the other). Your method must run in O(n log(n)) with n the size
     * of the teams.
     * 
     * @param teamA height of the players in team A
     * @param teamB height of the players in team B
     * @return the sum of the difference between players on the same spot. If 
     *         no arrangement can be found, returns -1
     */
    public static int canTakePictures(int [] teamA, int [] teamB) {
        sortArray(teamA);
        sortArray(teamB);

        boolean teamABig = teamA[0] > teamB[0];
        int totalHeight = 0;

        if (teamABig){
            for (int a = 0; a < teamA.length; a++){
                if (teamA[a] < teamB[a]){return -1;}
                totalHeight += (teamB[a] - teamA[a]);
            }
        } else {
            for (int a = 0; a < teamA.length; a++){
                if (teamA[a] > teamB[a]){return -1;}
                totalHeight += (teamB[a] - teamA[a]);
            }
        }


        return Math.abs(totalHeight);
    }
}
