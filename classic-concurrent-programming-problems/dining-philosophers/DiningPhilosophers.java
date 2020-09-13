import java.util.concurrent.*;

/**
 * The Dining Philosophers problem without deadlock
 *
 * @author Letícia Mazzo Portela
 * @since Sep/2020
 */
public class DiningPhilosophers {
    public static void main(String[] args) throws Exception {
        Philosopher[] philosophers = new Philosopher[5];
        Semaphore[] forks = new Semaphore[philosophers.length];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Semaphore(1);
        }

        for (int i = 0; i < philosophers.length; i++) {
            Semaphore leftFork = forks[i];
            Semaphore rightFork = forks[(i + 1) % forks.length];

            if (i == philosophers.length - 1) {
                philosophers[i] = new Philosopher(rightFork, leftFork);
            } else {
                philosophers[i] = new Philosopher(leftFork, rightFork);
            }

            Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
            t.start();
        }
    }
}
