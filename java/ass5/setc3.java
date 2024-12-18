public import java.util.Stack;

class SharedStack {
    private final Stack<Integer> stack = new Stack<>();
    private static final int MAX_SIZE = 10;

    // Method to push elements onto the stack
    public synchronized void push(int value) {
        while (stack.size() == MAX_SIZE) {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting to push. Stack is full.");
                wait(); // Wait if stack is full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Push operation interrupted.");
            }
        }
        stack.push(value);
        System.out.println(Thread.currentThread().getName() + " pushed: " + value);
        notifyAll(); // Notify waiting threads
    }

    // Method to pop elements off the stack
    public synchronized int pop() {
        while (stack.isEmpty()) {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting to pop. Stack is empty.");
                wait(); // Wait if stack is empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Pop operation interrupted.");
            }
        }
        int value = stack.pop();
        System.out.println(Thread.currentThread().getName() + " popped: " + value);
        notifyAll(); // Notify waiting threads
        return value;
    }
}

class Pusher implements Runnable {
    private final SharedStack stack;

    public Pusher(SharedStack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) { // Push 20 elements
            stack.push(i);
            try {
                Thread.sleep(100); // Simulate time taken to push
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Popper implements Runnable {
    private final SharedStack stack;

    public Popper(SharedStack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 40; i++) { // Pop 40 elements
            stack.pop();
            try {
                Thread.sleep(150); // Simulate time taken to pop
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class setc3 {
    public static void main(String[] args) {
        SharedStack sharedStack = new SharedStack();

        // Create two pusher threads
        Thread pusher1 = new Thread(new Pusher(sharedStack), "Pusher-1");
        Thread pusher2 = new Thread(new Pusher(sharedStack), "Pusher-2");

        // Create one popper thread
        Thread popper = new Thread(new Popper(sharedStack), "Popper");

        // Start the threads
        pusher1.start();
        pusher2.start();
        popper.start();

        // Wait for threads to finish
        try {
            pusher1.join();
            pusher2.join();
            popper.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("All threads have finished.");
    }
}
 {
    
}
