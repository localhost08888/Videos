import java.util.*;

public class seta2 {
    public static void main(String[] args) {
        // Create a LinkedList containing colors
        LinkedList<String> colors = new LinkedList<>();
        colors.add("red");
        colors.add("blue");
        colors.add("yellow");
        colors.add("orange");

        // i. Display the contents of the List using an Iterator
        System.out.println("Displaying colors using Iterator:");
        Iterator<String> iterator = colors.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        // ii. Display the contents of the List in reverse order using a ListIterator
        System.out.println("\nDisplaying colors in reverse order using ListIterator:");
        ListIterator<String> listIterator = colors.listIterator(colors.size());
        while (listIterator.hasPrevious()) {
            System.out.println(listIterator.previous());
        }

        // iii. Create another list and insert its elements between blue and yellow
        LinkedList<String> newColors = new LinkedList<>();
        newColors.add("pink");
        newColors.add("green");

        // Find the index of "yellow" and insert new colors before it
        int index = colors.indexOf("yellow");
        colors.addAll(index, newColors);

        // Display the updated list
        System.out.println("\nUpdated list after adding new colors:");
        for (String color : colors) {
            System.out.println(color);
        }
    }
}
