package searching;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Skyline {


    /**
     *   The buildings are defined with triplets (left, height, right).
     *         int[][] buildings = {{1, 11, 5}, {2, 6, 7}, {3, 13, 9}, {12, 7, 16}, {14, 3, 25}, {19, 18, 22}, {23, 13, 29}, {24, 4, 28}};
     *
     *         [{1,11},{3,13},{9,0},{12,7},{16,3},{19,18},{22,3},{23,13},{29,0}]
     *
     * @param buildings
     * @return  the skyline in the form of a list of "key points [x, height]".
     *          A key point is the left endpoint of a horizontal line segment.
     *          The key points are sorted by their x-coordinate in the list.
     */
    public static List<int[]> getSkyline(int[][] buildings) {
        // Create a list of points.
        BuildingPoint[] points = new BuildingPoint[buildings.length * 2];
        int index = 0;
        for (int[] building : buildings) {
            points[index] = new BuildingPoint(building[0], true, building[1]);
            points[index + 1] = new BuildingPoint(building[2], false, building[1]);
            index += 2;
        }

        Arrays.sort(points); // Sort the points.

        // Use a tree map to represent the active buildings.
        TreeMap<Integer, Integer> queue = new TreeMap<>();
        queue.put(0, 1); // Add a ground level (height 0).
        int prevMaxHeight = 0;

        List<int[]> result = new ArrayList<>();

        for (BuildingPoint point : points) {
            if (point.isStart) {
                // If it's a start point, add the height to the map, or increment the existing height's count.
                queue.compute(point.height, (key, value) -> {
                    if (value != null) return value + 1;
                    return 1;
                });
            } else {
                // If it's an end point, decrement or remove the height from the map.
                queue.compute(point.height, (key, value) -> {
                    if (value == 1) return null;
                    return value - 1;
                });
            }
            // Get current max height after the addition/removal above.
            int currentMaxHeight = queue.lastKey();

            // If the current max height is different from the previous one, we have a critical point.
            if (prevMaxHeight != currentMaxHeight) {
                result.add(new int[]{point.x, currentMaxHeight});
                prevMaxHeight = currentMaxHeight;
            }
        }

        return result;
    }

}
