package sorting;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Your task involves creating an `aggregate` function.
 * It takes as input a 2D array of integer, partitions the lines depending on the value of a chosen column,
 * and return a new 2D array of integer with one line per partition.
 *
 * When merging multiple rows into a single one,
 * multiple values in each column need to be combined into a single value.
 * Various options can be considered for this merge, such as calculating the mean, median, etc.
 * However, in this particular exercise, we specifically request the implementation of the `mode` function (most frequent value).
 * In this context, for each column, the selected value is the one that appears most frequently within the group.
 *
 * ! In a group, if two different values has the same number of occurrences, the smallest one is chosen.
 * ! The groups must be sorted in ascending order.
 * ! The order of the columns must not change.
 *
 * Consider this example, where an array is aggregated following its first column.
 *                {{1, 4, 6},
 *                 {2, 1, 4},              {{1, 4, 7},
 *                 {2, 2, 4},       ->      {2, 1, 4},
 *                 {1, 4, 7},               {3, 3, 5}}
 *                 {3, 3, 5},
 *                 {1, 5, 7}}
 *                Input array             Aggregated array
 *
 * In this example, there are 3 groups, for the three values in the first column; "1", "2", and "3".
 * The group "1" looks like   {{1, 4, 6},
 *                             {1, 4, 7},
 *                             {1, 5, 7}}
 *
 * For the second column, 4 appears twice and 5 once. Hence, the mode of this column is 4.
 * The mode of the third column is 7. When aggregated, this group will become {1, 4, 7}.
 *
 * For the second column of the group "2", both "1" and "2" are present one time.
 * As 1 < 2, "1" is chosen.
 *
 * The aggregate function should execute in O(n.m + n.log(n)) where n is the number of rows and m the number of columns.
 * The mode function should execute in O(n) where n is the number of rows.
 *
 * Debug your code on the small examples in the test suite.
 */
public class Aggregate {

    /**
     * Compute the element in an array that occurs most frequently within a specified range.
     * If there is a tie, the smallest value is returned.
     * The subset of values to consider in the array is determined by three parameters: the column to consider,
     * and the indexes of the first and last lines to consider.
     *
     * Your method should execute in O(n) where n is the number of lines to consider.
     *
     * @param array a 2D array
     * @param from the first line to consider
     * @param to the last line to consider
     * @param column the column to consider
     * @return the value with the most occurrences
     *
     * Example:
     * Consider a 2D array:
     * {
     *     {1, 5, 3},
     *     {4, 5, 2},
     *     {6, 2, 8}
     *     {9, 2, 2}
     *     {1, 5, 3}
     * }
     * Calling mode(array, 1, 4, 1) will analyze the second column (indexes 1 to 4).
     * The values considered in this column are thus: 5, 2, 2, 5.
     * The method returns 2 since it appears most frequently (2 times) in the specified range.
     * There is a tie between 2 and 5, but 2 is smaller.
     */
    public static int mode(int[][] array, int from, int to, int column) {
          HashMap<Integer, Integer> map = new HashMap<>();
          for (int a = from; a <= to; a++){
               int val = array[a][column];
               map.put(val, map.getOrDefault(val, 0) + 1);
          }

          int mostFreq = Integer.MAX_VALUE, occ = 0;
          for (Map.Entry<Integer, Integer> entry : map.entrySet()){
               int value = entry.getKey();
               int freq = entry.getValue();
               if (freq > occ) {mostFreq = value; occ = freq;}
               else if (freq == occ) mostFreq = (mostFreq < value) ? mostFreq : value;
          }

         return mostFreq;
    }

    /**
     * Aggregates values in a 2D integer array based on the value of a specified column.
     * This method partitions the array into groups, where each group consists of rows that have the same value
     * in the specified column. Within each group, it calculates the mode (most frequent value) for each column.
     * If there is a tie for the most frequent value, the smallest value is chosen. The resulting array contains
     * one row for each group, with the mode value for each column. The groups are sorted in ascending order based
     * on the values in the specified column.
     *
     * @param input The 2D integer array to aggregate.
     * @param column The column of the column based on which the array is partitioned into groups.
     * @return A new 2D integer array where each row represents an aggregated group.
     *
     * Example: See above and see unit tests.
     */
     public static int[][] aggregate(int[][] input, int column) {
          quickSort(input, column);
          int currVal = input[0][column];
          int currMin = 0;
          HashMap<Integer, int[]> hash = new HashMap<>(); //stores the value + range of lines to consider
          for (int a = 0; a < input.length; a++){
               if (currVal != input[a][column]) {
                    hash.put(currVal, new int[]{currMin, a - 1}); currVal = input[a][column]; currMin = a;
               }
          }
          hash.put(currVal, new int[]{currMin, input.length - 1});
          Set<Integer> hashSet = hash.keySet();
          int[][] output = new int[hash.size()][input[0].length];
          int row = 0;
          for (Integer value : hashSet){
               for (int col = 0; col < input[0].length; col++){
                    output[row][col] = mode(input, hash.get(value)[0], hash.get(value)[1], col);
               }
               row++;
          }
          quickSort(output, column);
          return output;
     }

     private static void quickSort(int[][] list, int col){
          if (isSorted(list, col)) return;
          sort(list, 0, list.length -1, col);
     }

     private static boolean isSorted(int[][] list, int col){
          for (int a = 0; a < list.length - 1; a++){
               if (list[a][col] > list[a+1][col]) return false;
          }
          return true;
     }

     private static void sort(int[][] list, int lo, int hi, int col){
          if (hi <= lo) return;
          int mid = partition(list, lo, hi, col);
          sort(list, lo, mid - 1, col);
          sort(list, mid + 1, hi, col);
     }

     private static int partition(int[][] list, int lo, int hi, int col){
          int i = lo, j = hi + 1;
          int value = list[lo][col];

          while (true){
               while (list[++i][col] < value) if (hi == i) break;
               while (value < list[--j][col]) if (lo == j) break;
               if (i >= j) break;
               exch(list, i, j); 
          }
          exch(list, lo, j);
          return j;
     }

     private static void exch(int[][] list, int x, int y){
          int[] tmp = list[x];
          list[x] = list[y];
          list[y] = tmp;
     }
}

