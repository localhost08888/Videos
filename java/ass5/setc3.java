import java.util.Stack;

class StackManipulator implements Runnable {
    private Stack<Integer> stack;
    private boolean isPushing;

    // Constructor to initialize stack and operation type (push or pop)
    public StackManipulator(Stack<Integer> stack, boolean isPushing) {
        this.stack = stack;
        this.isPushing = isPushing;
    }

    @Override
    public void run() {
        if (isPushing) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(500); // Simulate delay
                    synchronized (stack) {
                        stack.push(i);
                        System.out.println(Thread.currentThread().getName() + " pushed: " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000); // Simulate delay
                    synchronized (stack) {
                        if (!stack.isEmpty()) {
                            int poppedValue = stack.pop();
                            System.out.println(Thread.currentThread().getName() + " popped: " + poppedValue);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class StackExample {
    public static void main(String[] args) {
        Stack<Integer> sharedStack = new Stack<>();

        // Create two threads to push elements onto the stack
        Thread pushThread1 = new Thread(new StackManipulator(sharedStack, true), "Pusher-1");
        Thread pushThread2 = new Thread(new StackManipulator(sharedStack, true), "Pusher-2");

        // Create a third thread to pop elements off the stack
        Thread popThread = new Thread(new StackManipulator(sharedStack, false), "Popper");

        // Start the threads
        pushThread1.start();
        pushThread2.start();
        popThread.start();

        // Wait for all threads to finish
        try {
            pushThread1.join();
            pushThread2.join();
            popThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Stack: " + sharedStack);
    }
}
