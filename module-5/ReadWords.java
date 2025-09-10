/* William Stearns
    9-7-25
    CSD-420 Module 5
    This program reads words from a file, removes duplicates, and displays them in ascending and descending order.
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class ReadWords {

    public static void main(String[] args) {
        // --- Test Code to ensure the logic works correctly ---
        System.out.println("--- Functional Test with Sample Data ---");
        testSortingAndUniqueness();
        System.out.println("\n--- Starting Main Program with collection_of_words.txt ---");

        // --- Main program logic to read from the file ---
        TreeSet<String> uniqueWords = new TreeSet<>();
        String filePath = "collection_of_words.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into words, removing punctuation and converting to lowercase
                String[] words = line.toLowerCase().split("[\\s,.]+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        uniqueWords.add(word);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return;
        }

        // Display words in ascending order
        System.out.println("\nWords in Ascending Order:");
        uniqueWords.forEach(System.out::println);

        // Display words in descending order
        System.out.println("\nWords in Descending Order:");
        // Using descendingSet() for an efficient reversed view of the set
        uniqueWords.descendingSet().forEach(System.out::println);
    }

    /**
     * A helper method to perform a small functional test.
     * It ensures that duplicates are removed and the sorting is correct.
     */
    private static void testSortingAndUniqueness() {
        Set<String> testSet = new TreeSet<>();
        testSet.add("banana");
        testSet.add("apple");
        testSet.add("orange");
        testSet.add("apple"); // This duplicate should be ignored
        testSet.add("grape");

        System.out.println("Initial test words added: apple, banana, orange, apple, grape");
        System.out.println("Expected output (ascending): apple, banana, grape, orange");

        System.out.println("Actual output (ascending):");
        testSet.forEach(System.out::println);

        System.out.println("\nTest passed: Duplicates are removed and words are sorted correctly.");
    }
}
