import java.util.concurrent.*;

/**
 * Multiplex
 *
 * @author Letícia Mazzo Portela
 * @since Sep/2020
 */
class Counter {
    static int count = 0;
}

class ThreadController extends Thread {
    Semaphore multiplex;
    String threadName;

    public ThreadController(Semaphore multiplex, String threadName) {
        this.multiplex = multiplex;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        try {
            while (Counter.count < 20) {
                multiplex.acquire();
                Counter.count++;
                System.out.println(threadName + " modify counter to: " + Counter.count);
                multiplex.release();
                Thread.sleep(500);
            }
        } catch (InterruptedException exc) {
            System.out.println("Exception: " + exc.getMessage());
        }
    }
}

class Multiplex {
    public static void main(String args[]) throws InterruptedException {
        final Thread[] threads = new Thread[6];
        Semaphore multiplex = new Semaphore(3);

        for (int n = 0; n < threads.length; n += 1) {
            final ThreadController t = new ThreadController(multiplex, "Thread"+n);
            threads[n] = new Thread(t);
            System.out.println("Start Thread " + "Thread"+n);

            threads[n].start();
        }
    }
}
