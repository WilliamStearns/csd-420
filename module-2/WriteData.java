/* William Stearns
   8-24-25
   CSD-420 Module 2
   This program writes an array of integers and an array of doubles to a file in append mode.
*/

import java.io.*;
import java.util.Random;

public class WriteData {
    public static void main(String[] args) {
        int n = 5;
        int[] arr = new int[n];
        double[] arr2 = new double[n];

        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt();    // random integers
            arr2[i] = random.nextDouble();   // random doubles
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("[stearns]datafile.dat", true))) {
            // Write integer array
            for (int i : arr) {
                writer.print(i + " ");
            }
            writer.println();

            // Write double array
            for (double d : arr2) {
                writer.print(d + " ");
            }
            writer.println(); // empty line to separate entries

            System.out.println("Arrays written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
