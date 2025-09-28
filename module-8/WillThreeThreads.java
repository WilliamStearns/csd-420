/* William Stearns
   9-28-25
   CSD-420 Module 8
   This program allows for three threads to run at once with included testing.
*/

import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;
import java.util.Random;

/**
 * This class demonstrates the use of three separate threads to generate and display
 * different types of characters into a single JTextArea.
 * Thread 1: Generates random lowercase letters.
 * Thread 2: Generates random numbers (0-9).
 * Thread 3: Generates random special characters (!, @, #, $, %, &, *).
 *
 * This file also includes an inner class for self-testing the character generation logic.
 */
public class WillThreeThreads extends JFrame {

    private final JTextArea displayArea;
    private static final int MIN_CHARACTERS = 10000;
    private static final Random random = new SecureRandom();

    /**
     * Constructs the main application window and initializes components.
     */
    public WillThreeThreads() {
        super("Will's Three Threads");

        // Set up the text area for display
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Set up the main window
        add(scrollPane, BorderLayout.CENTER);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
    }

    /**
     * Starts the three character-generating threads.
     */
    public void startThreads() {
        // Create runnable tasks for each character type
        Runnable letterGenerator = createCharacterGenerator("abcdefghijklmnopqrstuvwxyz");
        Runnable numberGenerator = createCharacterGenerator("0123456789");
        Runnable specialCharGenerator = createCharacterGenerator("!@#$%&*");

        // Create and start a thread for each task
        Thread letterThread = new Thread(letterGenerator);
        Thread numberThread = new Thread(numberGenerator);
        Thread specialCharThread = new Thread(specialCharGenerator);

        letterThread.start();
        numberThread.start();
        specialCharThread.start();
    }

    /**
     * A factory method to create a Runnable that generates random characters.
     *
     * @param charSet The string of characters to choose from.
     * @return A Runnable task that, when executed, will generate and display characters.
     */
    private Runnable createCharacterGenerator(final String charSet) {
        return () -> {
            for (int i = 0; i < MIN_CHARACTERS; i++) {
                // Get a random character from the character set
                char randomChar = charSet.charAt(random.nextInt(charSet.length()));

                // Safely update the GUI from a worker thread
                SwingUtilities.invokeLater(() -> displayArea.append(String.valueOf(randomChar)));

                // A small delay to make the character generation visible in real-time
                try {
                    Thread.sleep(1); // sleep for 1 millisecond
                } catch (InterruptedException e) {
                    // Restore the interrupted status
                    Thread.currentThread().interrupt();
                    System.err.println("Thread interrupted: " + e.getMessage());
                }
            }
        };
    }

    /**
     * A static nested class to contain testing logic for character generation.
     */
    public static class CharacterGeneratorTests {
        private static final int TEST_CHAR_COUNT = 10000;
        private static final Random testRandom = new Random();

        private String generateTestCharacters(String charSet) {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < TEST_CHAR_COUNT; i++) {
                char randomChar = charSet.charAt(testRandom.nextInt(charSet.length()));
                output.append(randomChar);
            }
            return output.toString();
        }

        public void testLetterGeneration() {
            System.out.println("Testing letter generation...");
            String letters = "abcdefghijklmnopqrstuvwxyz";
            String result = generateTestCharacters(letters);
            assert result.length() == TEST_CHAR_COUNT : "Letter test: Incorrect length";
            for (char c : result.toCharArray()) {
                assert letters.indexOf(c) != -1 : "Letter test: Invalid character '" + c + "'";
            }
            System.out.println("Letter generation test passed.");
        }

        public void testNumberGeneration() {
            System.out.println("Testing number generation...");
            String numbers = "0123456789";
            String result = generateTestCharacters(numbers);
            assert result.length() == TEST_CHAR_COUNT : "Number test: Incorrect length";
            for (char c : result.toCharArray()) {
                assert numbers.indexOf(c) != -1 : "Number test: Invalid character '" + c + "'";
            }
            System.out.println("Number generation test passed.");
        }

        public void testSpecialCharacterGeneration() {
            System.out.println("Testing special character generation...");
            String specialChars = "!@#$%&*";
            String result = generateTestCharacters(specialChars);
            assert result.length() == TEST_CHAR_COUNT : "Special char test: Incorrect length";
            for (char c : result.toCharArray()) {
                assert specialChars.indexOf(c) != -1 : "Special char test: Invalid character '" + c + "'";
            }
            System.out.println("Special character generation test passed.");
        }

        public void runAllTests() {
            System.out.println("--- Running All Internal Tests ---");
            testLetterGeneration();
            testNumberGeneration();
            testSpecialCharacterGeneration();
            System.out.println("--- All Internal Tests Passed Successfully ---");
        }
    }


    /**
     * The main entry point for the application.
     *
     * @param args Command line arguments. If "test" is passed, it runs tests and exits.
     */
    public static void main(String[] args) {
        // Check if the first argument is "test" to run tests
        if (args.length > 0 && "test".equalsIgnoreCase(args[0])) {
            CharacterGeneratorTests tester = new CharacterGeneratorTests();
            tester.runAllTests();
        } else {
            // If not testing, run the GUI application
            SwingUtilities.invokeLater(() -> {
                WillThreeThreads app = new WillThreeThreads();
                app.setVisible(true);
                app.startThreads();
            });
        }
    }
}

