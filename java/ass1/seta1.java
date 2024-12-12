import java.util.*;

public class seta1 
{
    public static void main(String[] args) {
        // Create a TreeSet to store integers
        Set<Integer> set = new TreeSet<>();

        // Scanner to accept input from the user
        Scanner scanner = new Scanner(System.in);
        
        // Ask user how many numbers to input
        System.out.print("Enter the number of integers: ");
        int n = scanner.nextInt();
        
        // Accept 'n' integers and add them to the TreeSet
        System.out.println("Enter the integers:");
        for (int i = 0; i < n; i++) {
            int number = scanner.nextInt();
            set.add(number);
        }
        
        // Display the elements in sorted order
        System.out.println("\nThe elements in sorted order:");
        for (int num : set) {
            System.out.println(num);
        }

        // Search for a particular element in the collection
        System.out.print("\nEnter an element to search for: ");
        int searchElement = scanner.nextInt();
        
        if (set.contains(searchElement)) {
            System.out.println("Element " + searchElement + " found in the collection.");
        } else {
            System.out.println("Element " + searchElement + " not found in the collection.");
        }

        scanner.close();
    }
}
