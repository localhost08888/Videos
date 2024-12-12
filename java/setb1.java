import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class CitySTDManager {
    private HashMap<String, String> cityMap = new HashMap<>();

    public CitySTDManager() {
        // Create JFrame
        JFrame frame = new JFrame("City STD Code Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Input Panel for city and code
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        
        JLabel cityLabel = new JLabel("City:");
        JTextField cityField = new JTextField();
        JLabel codeLabel = new JLabel("STD Code:");
        JTextField codeField = new JTextField();
        
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");

        inputPanel.add(cityLabel);
        inputPanel.add(cityField);
        inputPanel.add(codeLabel);
        inputPanel.add(codeField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        
        JLabel searchLabel = new JLabel("Search City:");
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        JLabel resultLabel = new JLabel("Result: ");
        
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        searchPanel.add(resultLabel, BorderLayout.SOUTH);

        // Add Panels to Frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(searchPanel, BorderLayout.CENTER);

        // Add Button Action
        addButton.addActionListener(e -> {
            String city = cityField.getText().trim();
            String code = codeField.getText().trim();

            if (city.isEmpty() || code.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "City and STD Code cannot be empty.");
            } else if (cityMap.containsKey(city)) {
                JOptionPane.showMessageDialog(frame, "City already exists.");
            } else {
                cityMap.put(city, code);
                JOptionPane.showMessageDialog(frame, "City added successfully.");
            }
        });

        // Remove Button Action
        removeButton.addActionListener(e -> {
            String city = cityField.getText().trim();
            if (city.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a city to remove.");
            } else if (cityMap.containsKey(city)) {
                cityMap.remove(city);
                JOptionPane.showMessageDialog(frame, "City removed successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "City not found.");
            }
        });

        // Search Button Action
        searchButton.addActionListener(e -> {
            String city = searchField.getText().trim();
            if (city.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a city to search.");
            } else if (cityMap.containsKey(city)) {
                resultLabel.setText("Result: " + city + " - " + cityMap.get(city));
            } else {
                resultLabel.setText("Result: City not found.");
            }
        });

        // Set frame visibility
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CitySTDManager::new);
    }
}
