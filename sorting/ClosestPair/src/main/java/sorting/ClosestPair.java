package sorting;

/**
 * Let a be an array of integers.
 * In this exercise we are interested in finding
 * the two entries i and j such that a[i] + a[j] is the closest from a target x.
 * In other words, there are no entries k,l such that |x - (a[i] + a[j])| > |x - (a[k] + a[l])|.
 * Note that we can have i = j.
 * Your program must return the values a[i] and a[j].
 *
 * For example let a = [5, 10, 1, 75, 150, 151, 155, 18, 75, 50, 30]
 *
 * then for x = 20, your program must return the array [10, 10],
 *      for x = 153 you must return [1, 151] and
 *      for x = 170 you must return [18, 151]
 */
public class ClosestPair {

    /**
      * Finds the pair of integers which sums up to the closest value of x
      *
      * @param a the array in which the value are looked for
      * @param x the target value for the sum
      */
    public static int[] closestPair(int [] a, int x) {
      /* Create an array with all the sums possible, sort it and then just binary search for it whenever necessary */
      quickSort(a);
      int lo = 0, hi = a.length -1;
      int[] rep = new int[]{a[lo], a[hi]};
      while (lo < hi){
        int diff = a[lo] + a[hi];
        if (diff < x) lo++;
        else if (diff > x) hi--;
        else {
          rep[0] = a[lo];
          rep[1] = a[hi];
          return rep;
        }
        if (Math.abs(x - a[lo] - a[hi]) < Math.abs(x - rep[0] - rep[1])){
          rep[0] = a[lo];
          rep[1] = a[hi];
        }
      }
      return rep; 
    }

    private static void quickSort(int[] list){
      if (isSorted(list)) return;
      sort(list, 0, list.length -1);
    }

    private static void sort(int[] list, int lo, int hi){
      if (hi <= lo) return;
      int mid = partition(list, lo, hi);
      sort(list, lo, mid - 1);
      sort(list, mid + 1, hi);
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
      int tmp = list[a];
      list[a] = list[b];
      list[b] = tmp;
    }

    private static boolean isSorted(int[] list){
      for (int a = 0; a < list.length - 1; a++){ if (list[a] > list[a+1]) return false; }
      return true;
    }

    
}
