/*
 * Will Stearns
 * 10-5-25
 * CSD-420 Module 10
 * 
 * FanManager.java
 * 
 * This program connects to a MySQL database and manages fan records.
 * It allows displaying and updating records from the 'fans' table in the 
 * 'databasedb' database. It includes a GUI interface and optional
 * command-line test mode to verify functionality.
 */

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Optional;

/* 
 * Simple object model to hold fan data 
 */
class Fan {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String favoriteTeam;

    public Fan(int id, String firstName, String lastName, String favoriteTeam) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.favoriteTeam = favoriteTeam;
    }

    // Getters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFavoriteTeam() { return favoriteTeam; }
}

/*
 * Data Access Object (DAO) for database operations
 * Handles all connections and SQL queries for the fans table
 */
class FanDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/databasedb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "student1";
    private static final String PASS = "pass";

    // Helper method to get a new database connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Retrieve a fan record by ID
    public static Optional<Fan> getFanById(int id) {
        String sql = "SELECT * FROM fans WHERE ID = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Fan fan = new Fan(
                        rs.getInt("ID"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("favoriteteam")
                );
                return Optional.of(fan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Update a fan record
    public static boolean updateFan(int id, String firstName, String lastName, String team) {
        String sql = "UPDATE fans SET firstname = ?, lastname = ?, favoriteteam = ? WHERE ID = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, team);
            ps.setInt(4, id);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

/*
 * GUI class for managing fan records
 */
public class FanManager extends JFrame {

    private JTextField txtID, txtFirstName, txtLastName, txtFavoriteTeam;
    private JButton btnDisplay, btnUpdate;

    public FanManager() {
        setTitle("Fan Manager");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 5, 5));

        // Add input fields and labels
        add(new JLabel("ID:"));
        txtID = new JTextField();
        add(txtID);

        add(new JLabel("First Name:"));
        txtFirstName = new JTextField();
        add(txtFirstName);

        add(new JLabel("Last Name:"));
        txtLastName = new JTextField();
        add(txtLastName);

        add(new JLabel("Favorite Team:"));
        txtFavoriteTeam = new JTextField();
        add(txtFavoriteTeam);

        // Add buttons
        btnDisplay = new JButton("Display");
        btnUpdate = new JButton("Update");
        add(btnDisplay);
        add(btnUpdate);

        // Connect button actions to methods
        btnDisplay.addActionListener(e -> displayRecord());
        btnUpdate.addActionListener(e -> updateRecord());

        setVisible(true);
    }

    // Parse ID input and validate numeric value
    private int parseId() {
        try {
            return Integer.parseInt(txtID.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    // Display record from database in text fields
    private void displayRecord() {
        int id = parseId();
        if (id == -1) return;

        Optional<Fan> fanOptional = FanDAO.getFanById(id);
        if (fanOptional.isPresent()) {
            Fan fan = fanOptional.get();
            txtFirstName.setText(fan.getFirstName());
            txtLastName.setText(fan.getLastName());
            txtFavoriteTeam.setText(fan.getFavoriteTeam());
        } else {
            JOptionPane.showMessageDialog(this, "No record found for ID " + id);
            txtFirstName.setText("");
            txtLastName.setText("");
            txtFavoriteTeam.setText("");
        }
    }

    // Update record in the database
    private void updateRecord() {
        int id = parseId();
        if (id == -1) return;

        boolean success = FanDAO.updateFan(id,
                txtFirstName.getText().trim(),
                txtLastName.getText().trim(),
                txtFavoriteTeam.getText().trim()
        );

        if (success) {
            JOptionPane.showMessageDialog(this, "Record updated successfully for ID " + id);
        } else {
            JOptionPane.showMessageDialog(this, "No record found to update for ID " + id);
        }
    }

    /*
     * Optional test suite to verify database operations
     * Run with command-line argument "test":
     * java FanManager test
     */
    public static void runTests() {
        System.out.println("=== Running Tests ===");

        // Test 1: Display existing ID
        System.out.println("Test 1: Display existing ID 3");
        FanDAO.getFanById(3).ifPresentOrElse(
            fan -> {
                System.out.println("  ID: " + fan.getId());
                System.out.println("  First Name: " + fan.getFirstName());
                System.out.println("  Last Name: " + fan.getLastName());
                System.out.println("  Favorite Team: " + fan.getFavoriteTeam());
            },
            () -> System.out.println("  No record found for ID 3")
        );
        System.out.println("---------------------------");

        // Test 2: Update existing record
        System.out.println("Test 2: Update ID 3 to Michael Johnson, Seahawks");
        boolean updated = FanDAO.updateFan(3, "Michael", "Johnson", "Seahawks");
        System.out.println(updated ? "  Update successful." : "  Update failed.");
        System.out.println("---------------------------");

        // Test 3: Verify updated record
        System.out.println("Test 3: Verify updated ID 3");
        FanDAO.getFanById(3).ifPresent(fan -> System.out.println("  New Team: " + fan.getFavoriteTeam()));
        System.out.println("---------------------------");

        // Test 4: Display non-existent ID
        System.out.println("Test 4: Display non-existent ID 999");
        FanDAO.getFanById(999).ifPresentOrElse(
            fan -> System.out.println("  Error: Found a fan that should not exist."),
            () -> System.out.println("  Correctly found no record for ID 999.")
        );
        System.out.println("---------------------------");

        System.out.println("=== Tests Completed ===");
    }

    public static void main(String[] args) {
        // Run tests if "test" argument is provided
        if (args.length > 0 && args[0].equalsIgnoreCase("test")) {
            runTests();
            return;
        }

        // Otherwise, launch GUI
        SwingUtilities.invokeLater(FanManager::new);
    }
}
