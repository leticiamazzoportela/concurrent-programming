import java.util.concurrent.*;

/**
 * Philosopher
 *
 * @author Letícia Mazzo Portela
 * @since Sep/2020
 */
public class Philosopher implements Runnable {
    Semaphore leftFork;
    Semaphore rightFork;

    Philosopher(Semaphore leftFork, Semaphore rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public void pickupLeftFork() {
        try {
            System.out.println(Thread.currentThread().getName() + " picked up left fork");
            leftFork.acquire();
            Thread.sleep(5000);

            pickupRightFork();
        } catch (InterruptedException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    public void pickupRightFork() {
        try {
            if (rightFork.availablePermits() == 0) {
                System.out.println(Thread.currentThread().getName() + " picked up right fork and now is eating");
                rightFork.acquire();
                Thread.sleep(5000);
                
                dropRightFork();
            }

        } catch (InterruptedException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    public void dropLeftFork() {
        System.out.println(
                Thread.currentThread().getName() + " dropped the left and returning to thinking in the life");
        leftFork.release();
    }

    public void dropRightFork() {
        if (rightFork.availablePermits() == 0) {
            System.out.println(Thread.currentThread().getName() + " dropped the right fork");
            rightFork.release();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + " thinking");
                Thread.sleep(500);

                pickupLeftFork();
                dropLeftFork();
            } catch (InterruptedException ex) {
                System.out.println("Exception: " + ex.getMessage());
                return;
            }
        }
    }
}
