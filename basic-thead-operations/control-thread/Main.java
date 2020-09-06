/**
 * Main
 * 
 * @author Letícia Mazzo Portela
 * @since Mar/2020
 */
public class Main {

    public static void main(final String[] args) {
        final Thread t1 = new ControlThread(Thread.currentThread().getName());
        final Thread t2 = new ControlThread(Thread.currentThread().getName());

        t1.start();
        t2.start();

        int min = 2;
        int max = 10;
        
        while(true) {
            int time = (int)(Math.random() * (max - min + 1) + min);
            
            if (time % 2 == 0) {
                t1.interrupt();
            } else {
                t2.interrupt();
            }
        }

    }
}