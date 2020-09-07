import java.util.concurrent.*;

/**
 * Exercise 02 - Rendezvous
 *
 * @author Letícia Mazzo Portela
 * @since Sep/2020
 */
class Operation {
    static boolean signal = false;
}

class ThreadController extends Thread {
    Semaphore semaphore;
    String threadName;

    public ThreadController(Semaphore semaphore, String threadName) {
        this.semaphore = semaphore;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        if (this.threadName.equals("Thread01")) {
            System.out.println("Starting " + threadName);

            try {
                System.out.println(threadName + " is waiting for a permit.");
                semaphore.acquire();
                System.out.println(threadName + " gets a permit.");

                Operation.signal = true;

                System.out.println(threadName + ": " + Operation.signal);
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

                Operation.signal = false;

                System.out.println(threadName + ": " + Operation.signal);
                Thread.sleep(10);
            } catch (InterruptedException exc) {
                System.out.println("Excpetion: " + exc.getMessage());
            }
            System.out.println(threadName + " releases the permit.");
            semaphore.release();
        }
    }
}

class Rendezvous {
    public static void main(String args[]) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);

        ThreadController t1 = new ThreadController(semaphore, "Thread01");
        ThreadController t2 = new ThreadController(semaphore, "Thread02");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
