import java.util.concurrent.locks.*;

/**
 * Counter accessed by multiple threads controlled by lock
 *
 * @author Letícia Mazzo Portela
 * @since Sep/2020
 */
class Counter {
    public int count = 0;
    private final int min, max;
    private final Lock lock;

    public Counter(Lock lock, int min, int max) {
        this.lock = lock;
        this.min = min;
        this.max = max;
    }

    public void increment() {
        lock.lock();

        try {
            if (count <= max) {
                count++;
            }
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();

        try {
            if (count >= min) {
                count--;
            }
        } finally {
            lock.unlock();
        }
    }
}

class ThreadController extends Thread {
    private final Counter counter;
    private final int min, max;

    ThreadController(Counter counter, int min, int max) {
        this.counter = counter;
        this.min = min;
        this.max = max;
    }

    @Override
    public void run() {
        final String name = Thread.currentThread().getName();

        while (counter.count <= max) {
            counter.increment();
            System.out.println("Counter increased to " + counter.count + " for thread " + name);
        }

        while (counter.count >= min) {
            counter.decrement();
            System.out.println("Counter decremented to " + counter.count + " for thread " + name);
        }
    }
}

class LockCounter {
    public static void main(final String[] args) {
        final Thread[] threads = new Thread[3];
        final Counter counter = new Counter(new ReentrantLock(), 1, 5);

        for (int n = 0; n < threads.length; n += 1) {
            final ThreadController tc = new ThreadController(counter, 1, 5);
            threads[n] = new Thread(tc);
            System.out.println("Start Thread " + threads[n].getName());
            threads[n].start();
        }
    }
}