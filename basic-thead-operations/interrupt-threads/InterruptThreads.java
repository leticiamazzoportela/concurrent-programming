import java.io.*;

/**
 *  InterruptThreads
 * @author Letícia Mazzo Portela
 * @since Mar/2020
 */
public class InterruptThreads extends Thread {
    public static void runThreeThreads() {
        final int min = 3;
        final int max = 10;

        for (int i = 0; i < 3; i++) {
            System.out.println("Executing Thread " + i);

            final Thread t = new Thread(() -> {
                final String tName = Thread.currentThread().getName();
                System.out.println("Start Thread " + tName);

                try {
                    final int time = (int) (Math.random() * (max - min + 1) + min);
                    Thread.currentThread();
                    Thread.sleep(time * 1000);
                    System.out.println("Stop Thread " + tName + " after " + time + "s");
                } catch (final InterruptedException e) {
                    System.out.println("Interrupting thread " + tName);
                    e.printStackTrace();
                }
            }, "" + i);

            t.start();
            t.interrupt();
        }
    }

    public static void runReadFileThread() {
        final Thread t = new Thread(() -> {
            System.out.println("Start Thread " + Thread.currentThread().getName());

            while (true) {
                try {
                    final File file = new File("./quotes.txt");
                    final BufferedReader br = new BufferedReader(new FileReader(file));
                    String st;

                    Thread.currentThread();
                    Thread.sleep(10000);

                    while ((st = br.readLine()) != null) {
                        System.out.println(st);
                    }
                    System.out.println("... Waiting 10 sec ...");
                } catch (final InterruptedException e) {
                    System.out.println("Interrupting Read File Thread");
                    e.printStackTrace();
                } catch (final FileNotFoundException e) {
                    e.printStackTrace();
                    break;
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
        t.interrupt();
    }

    public static void main(final String[] args) {
        runThreeThreads();
        runReadFileThread();
    }
}