/* William Stearns
   8-24-25
   CSD-420 Module 2
   This program reads and displays data from a file.
*/

import java.io.*;

public class ReadData {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("stearnsdatafile.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
