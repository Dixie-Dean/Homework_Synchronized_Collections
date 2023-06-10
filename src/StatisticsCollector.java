import java.util.concurrent.ArrayBlockingQueue;

public class StatisticsCollector implements Runnable {
    private final char aChar;
    private final ArrayBlockingQueue<String> queue;
    private String result;
    private int maxValue = 0;
    private final int iterations;

    public StatisticsCollector(char aChar, ArrayBlockingQueue<String> queue, int iterations) {
        this.aChar = aChar;
        this.queue = queue;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            try {
                String string = queue.take();

                int counter = 0;
                for (int j = 0; j < string.length(); j++) {
                    if (string.charAt(j) == aChar) {
                        counter++;
                    }
                }

                if (maxValue < counter) {
                    result = string;
                    maxValue = counter;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.printf("RESULT: %s | %c : %d \n", result.substring(0, 10), aChar, maxValue);
    }
}
