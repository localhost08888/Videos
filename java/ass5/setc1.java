import java.io.File;

class FileWatcher implements Runnable {
    private final String fileName;

    public FileWatcher(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        System.out.println("Started watching file: " + fileName);
        while (true) {
            File file = new File(fileName);
            if (file.exists()) {
                System.out.println("File found: " + fileName + ". Thread ending.");
                break; // Exit the loop and terminate the thread
            } else {
                System.out.println("File not found: " + fileName + ". Checking again in 5 seconds...");
            }
            try {
                Thread.sleep(5000); // Wait for 5 seconds before checking again
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted for file: " + fileName);
                break;
            }
        }
    }
}

public class setc1{
    public static void main(String[] args) {
        // Example list of filenames to watch
        String[] fileNames = {
            "example1.txt",
            "example2.txt",
            "example3.txt"
        };

        // Create and start a thread for each file
        for (String fileName : fileNames) {
            Thread thread = new Thread(new FileWatcher(fileName));
            thread.start();
        }
    }
}
