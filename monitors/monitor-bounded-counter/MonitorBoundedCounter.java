/**
 * MonitorBoundedCounter
 *
 * @author Letícia Mazzo Portela
 * @since Aug/2020
 */
class BoundedCounter extends Thread {
    private int count = 0, min = 3, max = 5;

    public synchronized void increment(String name) {
        while (count == max) {
            try {
                System.out.println("Thread " + name + " blocked");
                wait();
            } catch (InterruptedException e) {
                System.out.println("Interrupting wait: " + e);
            }
        }
        count += 1;
        notify();
    }

    public synchronized void decrement(String name) {
        while (count == min) {
            try {
                System.out.println("Thread " + name + " blocked");
                wait();
            } catch (InterruptedException e) {
                System.out.println("Interrupting wait: " + e);
            }
        }
        count -= 1;
        notify();
    }

    @Override
    public void run() {
        final String name = Thread.currentThread().getName();

        while (count <= max) {
            increment(name);
            System.out.println("Counter increased to " + count + " for thread " + name);
        }

        while (count >= min) {
            decrement(name);
            System.out.println("Counter decremented to " + count + " for thread " + name);
        }
    }
}

class MonitorBoundedCounter {
    public static void main(final String[] args) {
        final Thread[] threads = new Thread[2];

        for (int n = 0; n < threads.length; n += 1) {
            final BoundedCounter tc = new BoundedCounter();
            threads[n] = new Thread(tc);
            System.out.println("Start Thread " + threads[n].getName());
            threads[n].start();
        }
    }
}