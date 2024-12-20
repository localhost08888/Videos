import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class FileSearcher extends Thread {
    private final File file;
    private final String searchString;
    private final List<String> results;

    public FileSearcher(File file, String searchString, List<String> results) {
        this.file = file;
        this.searchString = searchString;
        this.results = results;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.contains(searchString)) {
                    synchronized (results) {
                        results.add(file.getName() + " - Line " + lineNumber + ": " + line.trim());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
        }
    }
}

public class SimpleSearchEngine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the search string from the user
        System.out.print("Enter the string to search: ");
        String searchString = scanner.nextLine();

        // Get all text files in the current folder
        File currentFolder = new File(".");
        File[] textFiles = currentFolder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (textFiles == null || textFiles.length == 0) {
            System.out.println("No text files found in the current folder.");
            return;
        }

        // List to store results
        List<String> results = Collections.synchronizedList(new ArrayList<>());

        // Create and start a thread for each file
        List<Thread> threads = new ArrayList<>();
        for (File file : textFiles) {
            FileSearcher searcher = new FileSearcher(file, searchString, results);
            threads.add(searcher);
            searcher.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }

        // Display the results
        if (results.isEmpty()) {
            System.out.println("No occurrences of \"" + searchString + "\" found in text files.");
        } else {
            System.out.println("Search results:");
            for (String result : results) {
                System.out.println(result);
            }
        }
    }
}
===========================================

    Save the code to a file named SimpleSearchEngine.java.
Compile the program: javac SimpleSearchEngine.java.
Place some .txt files in the same folder as the program.
Run the program: java SimpleSearchEngine.
