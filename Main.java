import java.util.*;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 1000;
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int countR = 0;
                for (char c : route.toCharArray()) {
                    if (c == 'R') {
                        countR++;
                    }
                }

                synchronized (sizeToFreq) {
                    sizeToFreq.put(countR, sizeToFreq.getOrDefault(countR, 0) + 1);
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int maxFreq = 0;
        int maxSize = 0;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                maxSize = entry.getKey();
            }
        }

        System.out.println("Самое частое количество повторений " + maxSize + " (встретилось " + maxFreq + " раз)");
        System.out.println("Другие размеры:");

        List<Integer> keys = new ArrayList<>(sizeToFreq.keySet());
        Collections.sort(keys);

        for (Integer size : keys) {
            if (size != maxSize) {
                System.out.println("- " + size + " (" + sizeToFreq.get(size) + " раз)");
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}