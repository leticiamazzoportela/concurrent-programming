import java.util.Random;
import java.util.concurrent.locks.*;

/**
 * SharedFifoQueue using Conditions to control whether the queue is empty or full
 *
 * @author Letícia Mazzo Portela
 * @since Sep/2020
 */
class SharedFifoQueue {
    private final int size;
    private Integer[] items;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private int start, end, totalItems;

    SharedFifoQueue(int size) {
        this.size = size;
        this.items = new Integer[size];
    }

    public void put(int n) throws InterruptedException {
        lock.lock();

        try {
            while (totalItems == size) {
                notFull.await();
            }

            items[start] = n;
            start = ++start % items.length;
            totalItems += 1;

            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Integer pop() throws InterruptedException {
        lock.lock();

        try {
            while (totalItems == 0) {
                notEmpty.await();
            }

            int element = items[end];
            end = ++end % items.length;
            totalItems -= 1;
            
            notFull.signal();
            return element;
        } finally {
            lock.unlock();
        }
    }
}

class ProducerThread extends Thread {
    private final SharedFifoQueue sfq;
    private final Random r = new Random();

    ProducerThread(SharedFifoQueue sfq) {
        this.sfq = sfq;
    }

    @Override
    public void run() {
        final String name = Thread.currentThread().getName();

        while (true) {
            try {
                sfq.put(r.nextInt((100 - 1) + 1) + 1);
                System.out.println("Thread " + name + " is producing...");

                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
    }
}

class ConsumerThread extends Thread {
    private final SharedFifoQueue sfq;

    ConsumerThread(SharedFifoQueue sfq) {
        this.sfq = sfq;
    }

    @Override
    public void run() {
        final String name = Thread.currentThread().getName();

        while (true) {
            try {
                final Integer element = sfq.pop();
                System.out.println("Thread " + name + " consumed the element " + element);

                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
    }
}

class Main {
    public static void main(final String[] args) {
        final Thread[] threads = new Thread[6];
        final SharedFifoQueue sqf = new SharedFifoQueue(20);

        for (int n = 1; n < threads.length; n += 2) {
            final ProducerThread pt = new ProducerThread(sqf);
            final ConsumerThread ct = new ConsumerThread(sqf);

            threads[n - 1] = new Thread(pt);
            threads[n] = new Thread(ct);

            System.out.println("Start Producer Thread: " + threads[n - 1].getName());
            threads[n - 1].start();

            System.out.println("Start Consumer Thread: " + threads[n].getName());
            threads[n].start();
        }
    }
}
