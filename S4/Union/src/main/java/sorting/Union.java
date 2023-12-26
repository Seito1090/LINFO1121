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

    /**
     * A class representing an interval with two integers. Hence the interval is
     * [min, max].
     */
    public static class Interval implements Comparable<Union.Interval> {

        public final int min;
        public final int max;

        public Interval(int min, int max) {
            assert (min <= max);
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(Object obj) {
            return ((Union.Interval) obj).min == min && ((Union.Interval) obj).max == max;
        }

        @Override
        public String toString() {
            return "[" + min + "," + max + "]";
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
        if (intervals.length == 1) return intervals;
        //approach : sort the intervals by their min value and then merge them (do the unions of the intervals if needed)
        //sort
        Arrays.sort(intervals);

        //new arraylist to store the result
        ArrayList<Interval> result = new ArrayList<>();
        //invariant : min is always the min of the current part/union and max is always the max of the current part/union
        int min = intervals[0].min;
        int max = intervals[0].max;

        //loop over the intervals and check if a unions is needed
        for (int a = 1; a < intervals.length ; ++a){
            if (intervals[a].min > max){ //no union needed
                result.add(new Interval(min, max));
                min = intervals[a].min;
                max = intervals[a].max;
            } else { //union needed ! check in which case we are : [1,4] [2,5] or [1,5] [2,4] -> just check the max value to get the max of the union (since min is guaranteed to be min)
                max = Math.max(max, intervals[a].max); //don't add it to result yet, we might need to do another union after
            }
        }
        result.add(new Interval(min, max));
        return result.toArray(new Interval[0]);
    }
}