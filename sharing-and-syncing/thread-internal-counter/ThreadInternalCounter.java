import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadInternalCounter
 *
 * @author Letícia Mazzo Portela
 * @since Aug/2020
 */
class ThreadCounter implements Runnable {
    ThreadLocal<AtomicInteger> count = ThreadLocal.withInitial(() -> new AtomicInteger(0))));
    
    @Override
    public void run() {
        do {
            System.out.println("Counter " + count.get() + " by thread " + Thread.currentThread().getName());
            count.get().incrementAndGet();
        } while (count.get().intValue() < 20);
    }
}

class ThreadInternalCounter {
    public static void main(final String[] args) {
        final Thread[] threads = new Thread[10];

        for (int n = 0; n < threads.length; n += 1) {
            final ThreadCounter tc = new ThreadCounter();
            threads[n] = new Thread(tc);
            System.out.println("Start Thread " + threads[n].getName());
            threads[n].start();
        }
    }
}