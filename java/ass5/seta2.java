import java.util.Random;

class SumCalculator extends Thread {
    private int[] array;
    private int start;
    private int end;
    private long sum;

    public SumCalculator(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    public void run() {
        sum = 0;
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
    }

    public long getSum() {
        return sum;
    }
}

public class seta2 {
    public static void main(String[] args) {
        int[] numbers = new int[1000];
        Random random = new Random();

        // Generate 1000 random integers
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100) + 1; // Random integers between 1 and 100
        }

        SumCalculator[] threads = new SumCalculator[10];
        int chunkSize = numbers.length / 10;

        // Create and start 10 threads
        for (int i = 0; i < threads.length; i++) {
            int start = i * chunkSize;
            int end = (i == threads.length - 1) ? numbers.length : start + chunkSize;
            threads[i] = new SumCalculator(numbers, start, end);
            threads[i].start();
        }

        // Wait for all threads to finish and calculate the total sum
        long totalSum = 0;
        for (SumCalculator thread : threads) {
            try {
                thread.join();
                totalSum += thread.getSum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Calculate average
        double average = totalSum / (double) numbers.length;

        System.out.println("Total Sum: " + totalSum);
        System.out.println("Average: " + average);
    }
}
