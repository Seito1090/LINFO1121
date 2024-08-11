package sorting;

import org.javagrader.Grade;
import org.javagrader.CustomGradingResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.*;

@Grade
public class CardSorterTest {

    public void test(int[] array) {
        LinkedListImpl l = new LinkedListImpl(array);

        CardSorter.sort(l);

        int pops = l.getnPops();
        int swaps = l.getnSwaps();

        assertTrue(l.isSorted());
        assertTrue(pops <= array.length * array.length);
        assertTrue(swaps <= array.length * array.length);

    }


    public void runAll(int[][] arrays)  {
        for (int[] i : arrays) {
            test(i);
        }
    }

    @Test
    public void testExample() {
        LinkedListImpl l = new LinkedListImpl(new int[]{7, 8, 2, 22, 102, 1});        
        CardSorter.sort(l);
        assertTrue(l.isSorted());
    }

    @Test
    @Grade(value = 100, custom = true, cpuTimeout = 1000)
    public void twoDifferent() throws CustomGradingResult {
        int[][] arrays = new int[25][100];

        Random r = new Random(582);

        for (int i = 0; i < 25; i++) {
            int a = r.nextInt(1000000);
            int b = r.nextInt(1000000);
            for (int j = 0; j < 100; j++)
                arrays[i][j] = r.nextBoolean() ? a : b;
        }

        runAll(arrays);
    }
}


