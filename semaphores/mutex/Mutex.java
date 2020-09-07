import java.util.concurrent.*;

/**
 * Mutex
 *
 * @author Letícia Mazzo Portela
 * @since Sep/2020
 */
class Counter {
    static int count = 0;
}

class ThreadController extends Thread {
    Semaphore mutex;
    String threadName;

    public ThreadController(Semaphore mutex, String threadName) {
        this.mutex = mutex;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        try {
            while (Counter.count < 10) {
                mutex.acquire();
                Counter.count++;
                System.out.println(threadName + " modify counter to: " + Counter.count);
                mutex.release();
                Thread.sleep(500);
            }
        } catch (InterruptedException exc) {
            System.out.println("Exception: " + exc.getMessage());
        }
    }
}

class Mutex {
    public static void main(String args[]) throws InterruptedException {
        final Thread[] threads = new Thread[4];
        Semaphore mutex = new Semaphore(1);

        for (int n = 0; n < threads.length; n += 1) {
            final ThreadController t = new ThreadController(mutex, "Thread"+n);
            threads[n] = new Thread(t);
            System.out.println("Start Thread " + "Thread"+n);

            threads[n].start();
        }
    }
}
