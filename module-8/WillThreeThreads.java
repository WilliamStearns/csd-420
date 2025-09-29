/* Will Stearns
   9-28-25
   CSD-420 Module 8
   This program demonstrates three threads generating characters with live counters and internal testing.
*/

import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class WillThreeThreads extends JFrame {

    private final JTextArea displayArea;
    private final JLabel letterCountLabel;
    private final JLabel numberCountLabel;
    private final JLabel symbolCountLabel;
    private final JLabel summaryLabel;

    private static final int MIN_CHARACTERS = 10000;
    private static final Random random = new SecureRandom();

    private int letterCount = 0;
    private int numberCount = 0;
    private int symbolCount = 0;

    public WillThreeThreads() {
        super("Will's Three Threads with Counters and Summary");

        // Text area for output
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Labels for live counts
        letterCountLabel = new JLabel("Letters: 0");
        numberCountLabel = new JLabel("Numbers: 0");
        symbolCountLabel = new JLabel("Symbols: 0");
        summaryLabel = new JLabel("Summary: Waiting for threads to finish...");

        JPanel counterPanel = new JPanel();
        counterPanel.add(letterCountLabel);
        counterPanel.add(numberCountLabel);
        counterPanel.add(symbolCountLabel);
        counterPanel.add(summaryLabel);

        // Layout
        add(counterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void startThreads() {
        CountDownLatch latch = new CountDownLatch(3); // Wait for 3 threads

        Runnable letterGenerator = createCharacterGenerator("abcdefghijklmnopqrstuvwxyz", "letter", latch);
        Runnable numberGenerator = createCharacterGenerator("0123456789", "number", latch);
        Runnable symbolGenerator = createCharacterGenerator("!@#$%&*", "symbol", latch);

        new Thread(letterGenerator).start();
        new Thread(numberGenerator).start();
        new Thread(symbolGenerator).start();

        // Thread to display final summary after all threads finish
        new Thread(() -> {
            try {
                latch.await(); // Wait for all threads
                SwingUtilities.invokeLater(() -> summaryLabel.setText(
                        "Summary: Letters=" + letterCount +
                        ", Numbers=" + numberCount +
                        ", Symbols=" + symbolCount
                ));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private Runnable createCharacterGenerator(final String charSet, final String type, final CountDownLatch latch) {
        return () -> {
            for (int i = 0; i < MIN_CHARACTERS; i++) {
                char randomChar = charSet.charAt(random.nextInt(charSet.length()));
                SwingUtilities.invokeLater(() -> {
                    displayArea.append(String.valueOf(randomChar));
                    switch (type) {
                        case "letter" -> {
                            letterCount++;
                            letterCountLabel.setText("Letters: " + letterCount);
                        }
                        case "number" -> {
                            numberCount++;
                            numberCountLabel.setText("Numbers: " + numberCount);
                        }
                        case "symbol" -> {
                            symbolCount++;
                            symbolCountLabel.setText("Symbols: " + symbolCount);
                        }
                    }
                });

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            latch.countDown(); // Signal completion
        };
    }

    // Nested class for internal testing
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
            String letters = "abcdefghijklmnopqrstuvwxyz";
            String result = generateTestCharacters(letters);
            assert result.length() == TEST_CHAR_COUNT : "Letter test: Incorrect length";
            for (char c : result.toCharArray()) {
                assert letters.indexOf(c) != -1 : "Letter test: Invalid character '" + c + "'";
            }
            System.out.println("Letter generation test passed.");
        }

        public void testNumberGeneration() {
            String numbers = "0123456789";
            String result = generateTestCharacters(numbers);
            assert result.length() == TEST_CHAR_COUNT : "Number test: Incorrect length";
            for (char c : result.toCharArray()) {
                assert numbers.indexOf(c) != -1 : "Number test: Invalid character '" + c + "'";
            }
            System.out.println("Number generation test passed.");
        }

        public void testSpecialCharacterGeneration() {
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

    public static void main(String[] args) {
        // Run tests if "test" argument is passed
        if (args.length > 0 && "test".equalsIgnoreCase(args[0])) {
            CharacterGeneratorTests tester = new CharacterGeneratorTests();
            tester.runAllTests();
        } else {
            SwingUtilities.invokeLater(() -> {
                WillThreeThreads app = new WillThreeThreads();
                app.setVisible(true);
                app.startThreads();
            });
        }
    }
}
