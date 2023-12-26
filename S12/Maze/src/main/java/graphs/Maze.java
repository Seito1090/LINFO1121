package graphs;



import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * We are interested in solving a maze represented
 * by a matrix of integers 0-1 of size nxm.
 * This matrix is a two-dimensional array.
 * An entry equal to '1' means that there
 * is a wall and therefore this position is not accessible,
 * while '0' means that the position is free.
 * We ask you to write a Java code to discover
 * the shortest path between two coordinates
 * on this matrix from (x1, y1) to (x2, y2).
 * The moves can only be vertical (up/down) or horizontal (left/right)
 * (not diagonal), one step at a time.
 * The result of the path is an Iterable of
 * coordinates from the origin to the destination.
 * These coordinates are represented by integers
 * between 0 and n * m-1, where an integer 'a'
 * represents the position x =a/m and y=a%m.
 * If the start or end position is a wall
 * or if there is no path, an empty Iterable must be returned.
 * The same applies if there is no path
 * between the origin and the destination.
 */
public class Maze {

    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {
        //Check if the start or the end positions are not a wall, or that the starting position and the end ones are not the same ones.
        if (maze[x1][y1] == 1 || maze[x2][y2] == 1 ) {
            return new LinkedList<>();
        }

        int maxX = maze.length;
        int maxY = maze[0].length;
        //Shortest path between 2 points with no negative weights -> BFS / Dijkstra ?
        //Init the priority queue, we work with integers since the representation is a bit finnicky
        LinkedList<Integer> pq = new LinkedList<>();

        //Create a list of possible movements so that we can iterate over it easier later on
        //In this case, we can only move up, down, left and right
        final int[][] positions = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};

        //Init the link array (that's the format they want the result to be in, normally do it in a matrix with distances to a node), it stores to which node the current one is linked to
        //We will also use another array to store if a node has been visited or not
        boolean[] visited = new boolean[maxX * maxY];
        int[] link = new int[maxX * maxY];

        //Set the starting node as visited
        visited[ind(x1, y1, maxY)] = true;

        //Add it to the priority queue
        pq.add(ind(x1, y1, maxY));

        boolean found = false;
        while (!pq.isEmpty() && !found){
            //Recup the current coordinates
            int current = pq.poll();
            int x = row(current, maxY);
            int y = col(current, maxY);

            //Iterate on the possible movements
            for (int a = 0; a < 4; a++){
                //Recup the new coordinates
                int newX = x + positions[a][0];
                int newY = y + positions[a][1];

                //Check if the new coordinates are valid
                if ((0 <= newX && newX < maxX) && (0 <= newY && newY < maxY) && maze[newX][newY] == 0){
                    //We can do stuff now that we are sure that we are in the maze and not in a wall
                    //Get the index of the new coordinates
                    int newidx = ind(newX, newY, maxY);

                    //Check if the node has been visited or not
                    if (!visited[newidx]){
                        //If not, set it as visited and add it to the priority queue
                        visited[newidx] = true;
                        pq.add(newidx);
                        //Set the link to the current node
                        link[newidx] = current;
                        //exit if we are at the end
                        if (newX == x2 && newY == y2){
                            found = true;
                        }
                    }
                }
            }
        }
        int source = ind(x1, y1, maxY);
        int destination = ind(x2, y2, maxY);

        //Create the list that will be returned
        LinkedList<Integer> path = new LinkedList<>();

        //If we found a path, we can reconstruct it
        if (!visited[destination]){return path;}
        while (destination != source){
            path.add(destination);
            destination = link[destination];
        }
        path.add(destination);
        Collections.reverse(path);
        return path;

    }

    public static int ind(int x, int y, int lg) {
        return x * lg + y;
    }

    public static int row(int pos, int mCols) {
        return pos / mCols;
    }

    public static int col(int pos, int mCols) {
        return pos % mCols;
    }

}
