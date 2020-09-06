/**
 * ThreeThreads
 * 
 * @author Letícia Mazzo Portela
 * @since Mar/2020
 */
public class ThreeThreads extends Thread {

    public static void main(final String[] args) {
        int min = 3;
        int max = 10;

        for (int i = 0; i < 3; i++) {
            System.out.println("Executing Thread " + i);

            final Thread t = new Thread(() -> {
                String tName = Thread.currentThread().getName();
                System.out.println("Start Thread " + tName);
                
                try {
                    int time = (int)(Math.random() * (max - min + 1) + min);
                    Thread.currentThread();
                    Thread.sleep(time * 1000);
                    System.out.println("Stop Thread after " + time + "s");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, ""+i);

            t.start();
        }

    }
}