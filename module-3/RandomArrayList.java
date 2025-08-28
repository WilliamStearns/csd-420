/* William Stearns
   8-31-25
   CSD-420 Module 3
   This program creates an ArrayList of random integers and then removes duplicates from it.
*/
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class RandomArrayList {
    // Method to remove duplicates from an ArrayList
    public static <E> ArrayList<E> removeDuplicates(ArrayList<E> list){
        ArrayList<E> uniqueList = new ArrayList<>();
        for (E element : list) {
            if (!uniqueList.contains(element)) {
                uniqueList.add(element);
            }
        }
        return uniqueList;
    }
    public static void main(String[] args) {
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        Random rand = new Random();
        // Fill the ArrayList with 50 random integers between 1 and 20.
        for (int i = 0; i < 50; i++) {
            int randomNumber = rand.nextInt(20) + 1; // Generates a random integer from 1 to 20
            randomNumbers.add(randomNumber);
        }
        // Sort the ArrayList for readability and display.
        Collections.sort(randomNumbers);
        System.out.println(randomNumbers.size() + " Numbers in Original ArrayList: " + randomNumbers);
        // Create new ArrayList without duplicates and display.
        ArrayList<Integer> uniqueNumbers = removeDuplicates(randomNumbers);
        System.out.println(uniqueNumbers.size() + " Numbers in New ArrayList without duplicates: " + uniqueNumbers);
    }
}