import java.util.*;

public class seta3{
    public static void main(String[] args) {
        // Create a Hashtable to store student names and percentages
        Hashtable<String, Double> studentTable = new Hashtable<>();

        // Add student names and percentages to the Hashtable
        studentTable.put("Alice", 85.5);
        studentTable.put("Bob", 92.0);
        studentTable.put("Charlie", 78.3);
        studentTable.put("Diana", 88.9);

        // Display the contents of the Hashtable
        System.out.println("Student Details:");
        for (Map.Entry<String, Double> entry : studentTable.entrySet()) {
            System.out.println("Name: " + entry.getKey() + ", Percentage: " + entry.getValue());
        }

        // Search for a specific student
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the name of the student to search: ");
        String studentName = scanner.nextLine();

        if (studentTable.containsKey(studentName)) {
            System.out.println(studentName + "'s Percentage: " + studentTable.get(studentName));
        } else {
            System.out.println("Student " + studentName + " not found in the records.");
        }

        scanner.close();
    }
}
