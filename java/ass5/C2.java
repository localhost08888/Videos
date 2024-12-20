class TrafficSignal implements Runnable {
    private String signalColor;
    private int duration;

    // Constructor to initialize the signal color and its duration
    public TrafficSignal(String signalColor, int duration) {
        this.signalColor = signalColor;
        this.duration = duration;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(signalColor + " light ON");
            try {
                Thread.sleep(duration);  // Light stays on for the specified duration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(signalColor + " light OFF");
        }
    }
}

public class SimpleTrafficSignal {
    public static void main(String[] args) {
        // Create threads for Red, Green, and Yellow signals with their durations
        Thread redLight = new Thread(new TrafficSignal("Red", 3000));  // Red for 3 seconds
        Thread greenLight = new Thread(new TrafficSignal("Green", 5000));  // Green for 5 seconds
        Thread yellowLight = new Thread(new TrafficSignal("Yellow", 2000));  // Yellow for 2 seconds

        // Start the threads
        redLight.start();
        greenLight.start();
        yellowLight.start();
    }
}
