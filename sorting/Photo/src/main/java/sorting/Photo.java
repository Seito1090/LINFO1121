package sorting;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

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
        if (teamA.length != teamB.length) return -1;
        int sum = 0;
        myQuickSort(teamA, 0, teamA.length - 1);
        myQuickSort(teamB, 0, teamB.length - 1);
        if (teamA[0] == teamB[0]) return -1;
        int highTeam = (teamA[0] > teamB[0]) ? 1 : 2;
        for (int a = 0; a < teamA.length; a++){
            switch (highTeam) {
                case 1:
                    if (teamA[a] <= teamB[a]) return -1;
                    sum += teamA[a] - teamB[a];
                    break;
                case 2:
                    if (teamB[a] <= teamA[a]) return -1;
                    sum += teamB[a] - teamA[a];
                    break;
                default:
                    break;
            }
        }
        return sum;
    }

    private static void myQuickSort(int[] team, int lo, int hi){
        Collections.shuffle(Arrays.asList(team)); /* Elliminate the input dependency */
        sort(team, 0, team.length - 1);
    }

    private static void sort(int[] team, int lo, int hi){
        if (hi <= lo) return;
        int mid = partition(team, lo, hi);
        sort(team, lo, mid -1);
        sort(team, mid + 1, hi);
    }

    private static int partition(int[] list, int lo, int hi){
        int i = lo, j = hi + 1;
        int value = list[lo];
        while (true){
            while (list[++i] < value) if (i == hi) break;
            while (value < list[--j]) if (j == lo) break;
            if (i >= j) break;
            exch(list, i, j);
        }
        exch(list, lo, j);
        return j;
    }

    private static void exch(int[] list, int a, int b){
        int temp = list[a];
        list[a] = list[b];
        list[b] = temp;
    }
}
