import java.io.File;

class FileWatcher extends Thread {
    private final String fileName;

    public FileWatcher(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        while (true) {
            File file = new File(fileName);
            if (file.exists()) {
                System.out.println("File found: " + fileName);
                break; // Exit the thread
            } else {
                System.out.println("File not found: " + fileName + ". Checking again in 5 seconds...");
                try {
                    Thread.sleep(5000); // Wait for 5 seconds
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted for file: " + fileName);
                }
            }
        }
    }
}

public class FileWatcherDemo {
    public static void main(String[] args) {
        // Array of file names to watch
        String[] fileNames = {"file1.txt", "file2.txt", "file3.txt"};

        // Start a thread for each file
        for (String fileName : fileNames) {
            FileWatcher watcher = new FileWatcher(fileName);
            watcher.start();
        }
    }
}
