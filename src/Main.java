import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {

    public static int STRINGS_NUMBER = 100_000;
    public static ArrayBlockingQueue<String> QUEUE_A = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> QUEUE_B = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> QUEUE_C = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            for (int i = 0; i < STRINGS_NUMBER; i++) {
                String text = generateText("abc", 100_000);
                try {
                    QUEUE_A.put(text);
                    QUEUE_B.put(text);
                    QUEUE_C.put(text);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        Thread aThread = new Thread(new StatisticsCollector('a', QUEUE_A, STRINGS_NUMBER));
        Thread bThread = new Thread(new StatisticsCollector('b', QUEUE_B, STRINGS_NUMBER));
        Thread cThread = new Thread(new StatisticsCollector('c', QUEUE_C, STRINGS_NUMBER));

        aThread.start();
        bThread.start();
        cThread.start();

        aThread.join();
        bThread.join();
        cThread.join();

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}