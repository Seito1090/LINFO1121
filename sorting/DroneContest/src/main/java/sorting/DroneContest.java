package sorting;
import java.util.*;
/**
 * Context
 * --------
 * You have been contacted by an organizer of a drone artistic figure contest, because he is facing a problem that is a bit too complicated for his Excel sheet.
 * During the contest, several participants are going to make exhibitions of their drone and beautiful figures. Each participant had to reserve a time slot
 * before the contest and indicate the maximum height at which his drone will fly during his performance.
 * As these are powerful drones, they can fly very high, and this can cause problems for civil aviation. The organization in charge of the Belgian airspace,
 * Skeyes, therefore asks you to send them the maximum height used by the drones at any given time (the "profile").
 * You want it to be as accurate as possible, because any reservation costs money...
 *
 * Example
 * -------
 * Let's take an example. Let's have the following reservations:
 *
 * +-------+-------+------+----------+
 * | Drone | Start | Stop | Altitude |
 * +-------+-------+------+----------+
 * | A     | 1     | 5    | 3        |
 * +-------+-------+------+----------+
 * | B     | 3     | 12   | 5        |
 * +-------+-------+------+----------+
 * | C     | 6     | 14   | 1        |
 * +-------+-------+------+----------+
 * | D     | 7     | 15   | 4        |
 * +-------+-------+------+----------+
 * | E     | 15    | 18   | 5        |
 * +-------+-------+------+----------+
 * | F     | 16    | 20   | 1        |
 * +-------+-------+------+----------+
 * | G     | 17    | 19   | 2        |
 * +-------+-------+------+----------+
 *
 * With these reservations, the profile is as follows:
 *
 * t ∈ [0,1[   ->  altitude = 0
 * t ∈ [1,3[   ->  altitude = 3
 * t ∈ [3,12[  ->  altitude = 5
 * t ∈ [12,15[ ->  altitude = 4
 * t ∈ [15,18[ ->  altitude = 5
 * t ∈ [18,19[ ->  altitude = 2
 * t ∈ [19,20[ ->  altitude = 1
 * t ∈ [20,∞[  ->  altitude = 0
 *
 * Details
 * -------
 * You need to implement a public static LinkedList<HeightChange> findHighest(Drone[] participants) method that takes as input an array of
 * Drone participants in the contest. Each drone is defined by a takeoff time (drone.start), landing time (drone.end) and a flight height (drone.height).
 * The drone is considered to be in flight during [drone.start,drone.end[.
 * You have the following properties:
 *
 *  1 ≤ drone.start < drone.end;
 *  1 ≤ drone.height;
 *  1 ≤ participants.length ≤100000.
 *
 *  As output, you need to provide the profile of the drones height as a function of time.
 *  This is a sequence of HeightChange objects that indicate that from the time change.time, the new maximum altitude is change.height.
 *  The HeightChange objects must be sorted by time (from smallest to largest time). The first change must have change.time=0.
 *  The last change must be when the last drone lands. Two successive changes must have different height.
 *
 *  Your profile must be optimal. In other words, given two successive changes a and b,
 *  the maximum altitude of drones flying between a.time (inclusive) and b.time (exclusive) is EXACTLY a.height.
 *  Therefore, there is a drone flying at altitude a.height between these two times.
 *
 */
public class DroneContest {

    /**
     * Given an array of participants (that starts their drones at a time given by drone.start (inclusive),
     * stops it at drone.end (exclusive) and goes at height drone.height),
     * output the heights changes for the use of Skeyes.
     * <p>
     * The first drone takes off strictly somewhere after time 0.
     * <p>
     * The height changes must be as described on INGInious.
     * Equivalently, as follows:
     * - They must be ordered by time
     * - The first height change must be at time 0, and at height 0.
     * - The last height change must be at the time the last drone stops (and thus must be at height 0!)
     * - Given two successive height changes A and B, the maximum height of any active drone between A.time (inclusive)
     * and B.time (exclusive) is EXACTLY A.height (i.e. there exists a drone with this height active between these
     * times). Moreover, A.height != B.height.
     */
    public static LinkedList<HeightChange> findHighest(Drone[] participants) {
        /* merge sort approach might be the best deal here since we have to make the union at some points */
        ArrayList<HeightChange> merged = myMergeSort(participants, 0, participants.length - 1);
        
        LinkedList<HeightChange> output = new LinkedList<>();
        output.add(new HeightChange(0,0));
        for (HeightChange node : merged){
            if (output.getLast().height != node.height) output.add(node);
        }
        return output;
    }  

    private static ArrayList<HeightChange> myMergeSort(Drone[] list,int lo, int hi){
        if (lo == hi){
            /* We split all the way to a single entry in the list, we can now create a new entry "HeightChange" */
            ArrayList<HeightChange> newEntry = new ArrayList<>();
            newEntry.add(new HeightChange(list[lo].start, list[lo].height));
            newEntry.add(new HeightChange(list[lo].end, 0));
            return newEntry;
        }
        /* if we are not dealing with single entries in the list, then we look at the left and right and merge them */

        int mid = (lo + hi) / 2;

        ArrayList<HeightChange> left = myMergeSort(list, lo, mid);
        ArrayList<HeightChange> right = myMergeSort(list, mid + 1, hi);

        return merge(left, right);
    }


    private static ArrayList<HeightChange> merge(ArrayList<HeightChange> left, ArrayList<HeightChange> right){
        int heightA = 0, heightB = 0, timeA = 0, timeB = 0;
        int idxA = 0, idxB = 0;
        ArrayList<HeightChange> merge = new ArrayList<>(left.size() + right.size());

        while (idxA < left.size() && idxB < right.size()){
            if (left.get(idxA).time < right.get(idxB).time){
                timeA = left.get(idxA).time;
                heightA = left.get(idxA).height;
                int maximum = max(heightA, heightB);
                customAdd(merge, timeA, maximum); // BUG left right size 4 mid was somwhere ard 20 22 ish heightB is still at 1 where it should be 0, fixed ?
                idxA++;
            } else if (left.get(idxA).time > right.get(idxB).time){
                timeB = right.get(idxB).time;
                heightB = right.get(idxB).height;
                int maximum = max(heightB, heightA);
                customAdd(merge, timeB, maximum);
                idxB++;
            } else {
                int toAdd = (left.get(idxA).height > right.get(idxB).height) ? 1 : 2;
                heightA = left.get(idxA).height;
                heightB = right.get(idxB).height;
                switch (toAdd) {
                    case 1:
                        timeA = left.get(idxA).time;
                        customAdd(merge, timeA, heightA);
                        break;
                    case 2: 
                        timeB = right.get(idxB).time;
                        customAdd(merge, timeB, heightB);
                        break;
                    default:
                        break;
                }
                idxA++;idxB++;
            }
        }

        /* case almost too regular, one of them is left hanging there with some nodes to process */
        while (idxA < left.size()){
            customAdd(merge, left.get(idxA).time, left.get(idxA).height);
            idxA++;
        }

        while (idxB < right.size()){
            customAdd(merge, right.get(idxB).time, right.get(idxB).height);
            idxB++;
        }

        return merge;
    }

    private static int max(int a, int b){ return a > b ? a : b; }

    private static void customAdd(ArrayList<HeightChange> list, int time, int height){
        HeightChange nextNode = new HeightChange(time, height);
        if (list.size() > 0 && list.get(list.size() - 1).height == height) return;
        if (list.size() > 0 && list.get(list.size() - 1).time == time) {
            /* Check the height */
            list.get(list.size() - 1).height = max(list.get(list.size() - 1).height, height);
            return;
        }
        list.add(nextNode);
    }

}

class HeightChange {
    public int time;
    public int height;

    public HeightChange(int t, int h) {
        time = t; height = h;
    }
    public String toString() {
        return "Time: " + time + ", Height: " + height;
    }
}

class Drone {
    public final int start;
    public final int end;
    public final int height;

    public Drone(int s, int e, int h) {
        start = s; end = e; height = h;
    }

}
