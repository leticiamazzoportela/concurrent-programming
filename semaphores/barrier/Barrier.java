import java.util.concurrent.*;

/**
 * Barrier
 *
 * @author Letícia Mazzo Portela
 * @since Sep/2020
 */
class ThreadController extends Thread {
    String threadName;
    CyclicBarrier barrier;

    public ThreadController(String threadName, CyclicBarrier barrier) {
        this.threadName = threadName;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(
                    "Thread " + this.threadName + " reached the barrier" + " - ID: " + Thread.currentThread().getId());
            this.barrier.await();
        } catch (BrokenBarrierException bbe) {
            System.out.println("barrier is broken");
            return;
        } catch (InterruptedException ie) {
            System.out.println("Exception: " + ie.getMessage());
            return;
        }
        System.out.println(
                "Thread " + this.threadName + " is free from the barrier" + " - ID: " + Thread.currentThread().getId());
    }
}

class Barrier {
    public static void main(String args[]) throws InterruptedException {
        final Thread[] threads = new Thread[5];
        Runnable action = new Runnable() {
            @Override
            public void run() {
                System.out.println("All threads reached the barrier! Last ID: " + Thread.currentThread().getId());
            }
        };
        final CyclicBarrier barrier = new CyclicBarrier(threads.length, action);

        for (int n = 0; n < threads.length; n += 1) {
            final ThreadController t = new ThreadController("Thread" + n, barrier);
            threads[n] = new Thread(t);
            System.out.println("Start Thread " + "Thread" + n);

            threads[n].start();
        }
    }
}
