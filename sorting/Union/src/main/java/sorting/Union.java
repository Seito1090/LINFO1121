package sorting;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * Given an array of (closed) intervals, you are asked to implement the union operation.
 * This operation will return the minimal array of sorted intervals covering exactly the union
 * of the points covered by the input intervals.
 * For example, the union of intervals [7,9],[5,8],[2,4] is [2,4],[5,9].
 * The Interval class allowing to store the intervals is provided
 * to you.
 *
 */
public class Union {
    private static Interval[] aux;

    /**
     * A class representing an interval with two integers. Hence the interval is
     * [min, max].
     */
    public static class Interval implements Comparable<Union.Interval> {

        public final int min;
        public final int max;

        public Interval(int min, int max) {
            assert(min <= max);
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(Object obj) {
            return ((Union.Interval) obj).min == min && ((Union.Interval) obj).max == max;
        }

        @Override
        public String toString() {
            return "["+min+","+max+"]";
        }

        @Override
        public int compareTo(Union.Interval o) {
            if (min < o.min) return -1;
            else if (min == o.min) return max - o.max;
            else return 1;
        }
    }

    /**
     * Returns the union of the intervals given in parameters.
     * This is the minimal array of (sorted) intervals covering
     * exactly the same points than the intervals in parameter.
     * 
     * @param intervals the intervals to unite.
     */
    public static Interval[] union(Interval[] intervals) {
        /* Idea : merge sort ! or another idea is quicksort to have each intervals in ascending order and then uniting them */
        if (intervals.length <= 1) return intervals;
        Interval[] output = mergeSort(intervals); // TODO make another sort (aka quicksort, rn it works but takes more space)
        return output;
    }

    private static Interval[] mergeSort(Interval[] list){
        aux = new Interval[list.length];
        sort(list, 0, list.length - 1);
        int lo = list[0].min, hi = list[0].max;
        ArrayList<Interval> output = new ArrayList<>();
        for (int a = 1; a < list.length; a++){
            if (list[a].min <= hi || list[a].min == lo) hi = list[a].max > hi ? list[a].max : hi;
            else {
                output.add(new Interval(lo, hi));
                lo = list[a].min ; hi = list[a].max;
            }
        }
        output.add(new Interval(lo,hi));
        return output.toArray(new Interval[0]);
    }

    private static void sort(Interval[] list, int lo, int hi){
        if (hi <= lo) return;
        int mid  = (hi + lo) / 2;
        sort(list, lo, mid);
        sort(list, mid + 1, hi);
        merge(list, lo, mid, hi);
    }

    private static void merge(Interval[] list, int lo, int mid, int hi){
        int i = lo, j = mid + 1;
        for (int a = lo; a <= hi; a++){
            aux[a] = list[a];
        }

        for (int k = lo; k <= hi; k++){
            if (i > mid) list[k] = aux[j++];
            else if (j > hi) list[k] = aux[i++];
            else if (aux[j].min < aux[i].min) list[k] = aux[j++];
            else list[k] = aux[i++];
        }
    }
}
