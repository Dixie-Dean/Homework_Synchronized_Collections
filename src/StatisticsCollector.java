import java.util.concurrent.ArrayBlockingQueue;

public class StatisticsCollector implements Runnable {
    private final char aChar;
    private final ArrayBlockingQueue<String> queue;
    private String result;
    private int maxValue = Integer.MIN_VALUE;

    public StatisticsCollector(char aChar, ArrayBlockingQueue<String> queue) {
        this.aChar = aChar;
        this.queue = queue;
    }

    @Override
    public void run() {
        do {
            try {
                String string = queue.take();

                int counter = 0;
                for (int i = 0; i < string.length(); i++) {
                    if (string.charAt(i) == aChar) {
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
        } while (queue.iterator().hasNext());

        System.out.printf("RESULT: %s | %c : %d \n", result.substring(0, 10), aChar, maxValue);
        //System.out.printf("RESULT: %c : %d \n", aChar, maxValue);
    }
}
