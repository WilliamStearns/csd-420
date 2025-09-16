/* William Stearns
    9-14-25
    CSD-420 Module 6
    This program implements the bubble sort algorithm using comparable and comparator interfaces.
*/
import java.util.Arrays;
import java.util.Comparator;

public class BubbleSortProgram {

    public static <E extends Comparable<E>> void bubbleSort(E[] list) {
        boolean swapped;
        for (int pass = 0; pass < list.length - 1; pass++) {
            swapped = false;
            for (int i = 0; i < list.length - 1 - pass; i++) {
                if (list[i].compareTo(list[i + 1]) > 0) {
                    // Swap elements
                    E temp = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; // Early exit if already sorted
        }
    }


    public static <E> void bubbleSort(E[] list, Comparator<? super E> comparator) {
        boolean swapped;
        for (int pass = 0; pass < list.length - 1; pass++) {
            swapped = false;
            for (int i = 0; i < list.length - 1 - pass; i++) {
                if (comparator.compare(list[i], list[i + 1]) > 0) {
                    // Swap elements
                    E temp = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    // A simple class for testing with a custom object
    static class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name + " (" + age + ")";
        }
    }

    public static void main(String[] args) {
        // --- Test with Comparable ---
        System.out.println("--- Testing with Comparable ---");
        Integer[] numbers = {4, 1, 9, 2, 7};
        System.out.println("Original array: " + Arrays.toString(numbers));
        BubbleSortProgram.bubbleSort(numbers);
        System.out.println("Sorted array:   " + Arrays.toString(numbers)); // [1, 2, 4, 7, 9]

        System.out.println();

        // --- Test with Comparator  ---
        System.out.println("--- Testing with Comparator ---");
        String[] words = {"Artpop", "Chromatica", "Joanne", "Mayhem"};
        System.out.println("Original array: " + Arrays.toString(words));
        BubbleSortProgram.bubbleSort(words, Comparator.reverseOrder());
        System.out.println("Sorted array:   " + Arrays.toString(words)); // [Mayhem, Joanne, Chromatica, Artpop]

        System.out.println();

        // --- Test with Custom Object and Comparator ---
        System.out.println("--- Testing with Custom Object and Comparator ---");
        Person[] people = {
            new Person("Charlie", 19),
            new Person("Alice", 55),
            new Person("Bob", 30)
        };
        System.out.println("Original array: " + Arrays.toString(people));

        // Sort by age using Comparator
        BubbleSortProgram.bubbleSort(people, Comparator.comparingInt(p -> p.age));

        System.out.println("Sorted array by age: " + Arrays.toString(people));
    }
}
