/* William Stearns
   8-31-25
   CSD-420 Module 4
   This program compares the traversal performance of a LinkedList using an
   Iterator versus the inefficient get(index) method.
*/

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LinkedListTest {

    public static void main(String[] args) {
        // Run a small test to confirm the logic is sound
        runFunctionalTest();

        // Perform the main performance tests
        testPerformance(50_000);
        testPerformance(500_000);
    }

    /**
     * A simple test to ensure the traversal logic works for both methods.
     */
    private static void runFunctionalTest() {
        System.out.println("--- Functional Test for small list ---");
        List<Integer> testList = new LinkedList<>();
        testList.add(10);
        testList.add(20);

        // Test with a for-each loop (using Iterator)
        System.out.print("Using for-each loop: ");
        for (int num : testList) {
            System.out.print(num + " ");
        }

        // Test with get(index) method
        System.out.print("\nUsing get(index): ");
        for (int i = 0; i < testList.size(); i++) {
            System.out.print(testList.get(i) + " ");
        }
        System.out.println("\n-----------------------------------\n");
    }

    /**
     * Measures and prints the traversal time for a LinkedList of a given size.
     * @param size The number of elements in the list.
     */
    private static void testPerformance(int size) {
        System.out.printf("--- Performance Test for %,d integers ---\n", size);

        // 1. Create and fill the list
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        // 2. Time the Iterator-based traversal
        long startIterator = System.nanoTime();
        // A for-each loop is the cleanest way to use an Iterator
        for (Integer num : list) {
            // This traversal is efficient because it simply follows the
            // next pointer of each node. It's an O(n) operation.
        }
        long endIterator = System.nanoTime();
        long durationIterator = endIterator - startIterator;
        System.out.printf("Iterator Traversal took: %,d nanoseconds (%.3f milliseconds)\n",
                durationIterator, TimeUnit.NANOSECONDS.toMicros(durationIterator) / 1000.0);

        // 3. Time the get(index) based traversal
        long startGet = System.nanoTime();
        for (int i = 0; i < list.size(); i++) {
            // This is very slow. get(i) starts from the beginning of the list
            // each time, making this an O(n^2) operation overall.
            list.get(i);
        }
        long endGet = System.nanoTime();
        long durationGet = endGet - startGet;
        System.out.printf("get(index) Traversal took: %,d nanoseconds (%.3f seconds)\n",
                durationGet, TimeUnit.NANOSECONDS.toSeconds(durationGet) / 1000.0);

        // 4. Explain the key difference
        System.out.println("\nResults Explanation:");
        System.out.println("- Iterator is significantly faster because it's a single O(n) pass.");
        System.out.println("- get(index) is extremely slow because it's an O(n^2) operation, as each lookup starts over.");
        System.out.println("--------------------------------------------------\n");
    }
}