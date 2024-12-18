class MessageThread extends Thread {
    private final String message;

    public MessageThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Thread Name: " + Thread.currentThread().getName() +
                    ", Message: " + message);
            try {
                Thread.sleep(1000); // Sleep for 1 second to avoid flooding the console
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + " interrupted.");
                return;
            }
        }
    }
}

public class ContinuousThreads {
    public static void main(String[] args) {
        // Create two threads with messages
        MessageThread thread1 = new MessageThread("Hello from Thread 1");
        MessageThread thread2 = new MessageThread("Greetings from Thread 2");

        // Start the threads
        thread1.start();
        thread2.start();

        // Display a message to let the user know how to exit
        System.out.println("Press Ctrl+C to stop the program...");
    }
}
===============================================================
Press Ctrl+C to stop the program...
Thread Name: Thread-0, Message: Hello from Thread 1
Thread Name: Thread-1, Message: Greetings from Thread 2
Thread Name: Thread-0, Message: Hello from Thread 1
Thread Name: Thread-1, Message: Greetings from Thread 2
