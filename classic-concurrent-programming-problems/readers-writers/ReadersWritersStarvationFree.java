import java.util.concurrent.*;

/**
 * Readers and Writers problem without Starvation
 *
 * @author Letícia Mazzo Portela
 * @since Sep/2020
 */
class ReaderWriter {
    int numWriters = 0, numReaders = 0, count = 0;
    Semaphore mutex = new Semaphore(1);
    Semaphore wlock = new Semaphore(1);
    Semaphore rlock = new Semaphore(1);

    public void startRead() throws InterruptedException {
        mutex.acquire();
        numReaders++;

        if (numReaders == 1) {
            wlock.acquire();
        }

        mutex.release();
    }

    public void endRead() throws InterruptedException {
        mutex.acquire();
        numReaders--;

        if (numReaders == 0) {
            wlock.release();
        }

        mutex.release();   
    }

    public void startWrite() throws InterruptedException {
        mutex.acquire();
        numWriters++;
        count++;

        if (numWriters == 1) {
            rlock.acquire();
        }

        mutex.release();
    }

    public void endWrite() throws InterruptedException {
        mutex.acquire();
        numWriters--;

        if (numWriters == 0) {
            rlock.release();
        }

        mutex.release();
    }
}

class Reader implements Runnable {
    ReaderWriter rw;
    String threadName;

    Reader(ReaderWriter rw, String threadName) {
        this.rw = rw;
        this.threadName = threadName;
    }

    public void run() {
        try {
            rw.startRead();
            System.out.println("Thread " + this.threadName + " is reading");
            System.out.println("Thread " + this.threadName + " read the value: " + rw.count);
            Thread.sleep(4000);
            rw.endRead();
        } catch (InterruptedException exc) {
            System.out.println("Exception: " + exc.getMessage());
        }
    }
}

class Writer implements Runnable {
    ReaderWriter rw;
    String threadName;

    Writer(ReaderWriter rw, String threadName) {
        this.rw = rw;
        this.threadName = threadName;
    }

    public void run() {
        try {
            rw.startWrite();
            System.out.println("Thread " + this.threadName + " is writing");
            Thread.sleep(4000);
            rw.endWrite();
        } catch (InterruptedException exc) {
            System.out.println("Exception: " + exc.getMessage());
        }
    }
}

class ReadersWritersStarvationFree {
    public static void main(String args[]) throws InterruptedException {
        final Thread[] threads = new Thread[6];
        final ReaderWriter rw = new ReaderWriter();

        for (int n = 1; n < threads.length; n += 2) {
            final Writer tWriter = new Writer(rw, "Thread" + (n - 1));
            final Reader tReader = new Reader(rw, "Thread" + n);

            threads[n - 1] = new Thread(tWriter);
            threads[n] = new Thread(tReader);

            System.out.println("Start Writer Thread " + "Thread" + (n - 1));
            threads[n - 1].start();

            System.out.println("Start Reader Thread " + "Thread" + n);
            threads[n].start();

        }
    }
}
