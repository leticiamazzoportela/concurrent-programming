import java.util.ArrayList;
import java.util.concurrent.locks.*;
import java.util.Random;

/**
 * ArrayListThreadSafe using ReadWriteLock
 *
 * @author Letícia Mazzo Portela
 * @since Sep/2020
 */
class ArrayListThreadSafe<T> {
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock readLock = rwl.readLock();
    private final Lock writeLock = rwl.writeLock();
    private final ArrayList<T> data = new ArrayList<T>();

    public void add(T item) {
        writeLock.lock();

        try {
            data.add(item);
        } finally {
            writeLock.unlock();
        }
    }

    public void remove(T item) {
        writeLock.lock();

        try {
            data.remove(item);
        } finally {
            writeLock.lock();
        }
    }

    public boolean contains(T item) {
        readLock.lock();

        try {
            return data.contains(item);
        } finally {
            readLock.unlock();
        }
    }

    public int size() {
        readLock.lock();

        try {
            return data.size();
        } finally {
            readLock.unlock();
        }
    }
}

class Reader extends Thread {
    private final ArrayListThreadSafe<Integer> alts;
    private final Random r = new Random();

    Reader(ArrayListThreadSafe<Integer> alts) {
        this.alts = alts;
    }

    public void run() {
        final String name = Thread.currentThread().getName();
        final Integer value = r.nextInt((100 - 1) + 1) + 1;

        while (true) {
            try {
                System.out.println("Thread " + name + " is reading...");
                System.out.println("Thread " + name + ": data contains the value " + value + "? " + alts.contains(value));
                System.out.println("Thread " + name + ": data size " + alts.size());
    
                Thread.sleep(3000);
            } catch (InterruptedException exc) {
                System.out.println("Exception: " + exc.getMessage());
            }
        }
    }
}

class Writer extends Thread {
    private final ArrayListThreadSafe<Integer> alts;
    private final Random r = new Random();

    Writer(ArrayListThreadSafe<Integer> alts) {
        this.alts = alts;
    }

    public void run() {
        final String name = Thread.currentThread().getName();
        final Integer value = r.nextInt((100 - 1) + 1) + 1;

        while (true) {
            try {
                System.out.println("Thread " + name + " is writing...");
                alts.add(value);
                System.out.println("Thread " + name + " added the value " + value);

                if (alts.size() > 10) {
                    alts.remove(value);
                    System.out.println("Thread " + name + "tried remove the value " + value);
                }

                Thread.sleep(3000);
            } catch (InterruptedException exc) {
                System.out.println("Exception: " + exc.getMessage());
            }
        }
    }
}

class Main {
    public static void main(String args[]) throws InterruptedException {
        final Thread[] threads = new Thread[6];
        final ArrayListThreadSafe<Integer> alts = new ArrayListThreadSafe<Integer>();

        for (int n = 1; n < threads.length; n += 2) {
            final Writer tWriter = new Writer(alts);
            final Reader tReader = new Reader(alts);

            threads[n - 1] = new Thread(tWriter);
            threads[n] = new Thread(tReader);

            System.out.println("Start Writer Thread " + threads[n - 1].getName());
            threads[n - 1].start();

            System.out.println("Start Reader Thread " + threads[n].getName());
            threads[n].start();

        }
    }
}
