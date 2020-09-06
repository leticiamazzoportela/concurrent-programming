/**
 * MonitorCounter
 *
 * @author Letícia Mazzo Portela
 * @since Aug/2020
 */
class Counter extends Thread {
    private int count = 0, max = 4;
    private boolean sleep = false;

    public synchronized void increment() {
        count += 1;
        notifyAll();
    }

    public synchronized void sleepUntil(int value) {
        while (count < value) {
            sleep = true;
            try {
                wait();
                System.out.println("waiting...");
            } catch (InterruptedException e) {
                System.out.println("Interrupting wait: " + e);
            }
        }
        sleep = false;
        notify();
    }

    @Override
    public void run() {
        sleepUntil(2);
        while (count < max) {
            increment();
            System.out.println("Counter is " + count + " for thread " + Thread.currentThread().getName());

        }
    }
}

class MonitorCounter {
    public static void main(final String[] args) {
        final Thread[] threads = new Thread[2];

        for (int n = 0; n < threads.length; n += 1) {
            final Counter tc = new Counter();
            threads[n] = new Thread(tc);
            System.out.println("Start Thread " + threads[n].getName());
            threads[n].start();
        }
    }
}