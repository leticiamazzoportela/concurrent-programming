import java.util.concurrent.*;

/**
 * Exercise 01 - SignalingSemaphore
 *
 * @author Letícia Mazzo Portela
 * @since Sep/2020
 */
class SignalManager {
    static boolean signal = false;
}

class ThreadManager extends Thread {
    Semaphore semaphore;
    String threadName;

    public ThreadManager(Semaphore semaphore, String threadName) {
        this.semaphore = semaphore;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        if (this.threadName.equals("Signaling Thread")) {
            System.out.println("Starting " + threadName);

            try {
                System.out.println(threadName + " is waiting for a permit.");
                semaphore.acquire();
                System.out.println(threadName + " gets a permit.");

                SignalManager.signal = true;

                System.out.println(threadName + ": " + SignalManager.signal);
                Thread.sleep(10);

            } catch (InterruptedException exc) {
                System.out.println("Excpetion: " + exc.getMessage());
            }

            System.out.println(threadName + " releases the permit.");
            semaphore.release();
        } else {
            System.out.println("Starting " + threadName);

            try {
                System.out.println(threadName + " is waiting for a permit.");
                semaphore.acquire();
                System.out.println(threadName + " gets a permit.");

                System.out.println(threadName + " get the signal: " + SignalManager.signal);
                Thread.sleep(10);
            } catch (InterruptedException exc) {
                System.out.println("Excpetion: " + exc.getMessage());
            }
            System.out.println(threadName + " releases the permit.");
            semaphore.release();
        }
    }
}

class SignalingSemaphore {
    public static void main(String args[]) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);

        ThreadManager t1 = new ThreadManager(semaphore, "Signaling Thread");
        ThreadManager t2 = new ThreadManager(semaphore, "Receiving Thread");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final Signal: " + SignalManager.signal);
    }
}
