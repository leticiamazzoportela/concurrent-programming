/**
 * ThreadsGroupOperations
 * 
 * @author Letícia Mazzo Portela
 * @since Mar/2020
 */
class ThreadOfGroup extends Thread {
    ThreadOfGroup(String threadName, ThreadGroup groupName) throws InterruptedException {
        super(groupName, threadName);

        System.out.println("Start Thread " + threadName);
        start();
    }

    @Override
    public void run() {
        int i = 0;

        while(i < 5) {
            try {
                Thread.sleep(5000);
                System.out.println("State of Thread " + Thread.currentThread().getName() + " in sleep: " + Thread.currentThread().getState());
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            i += 1;
        }

        System.out.println("Finish Thread " + Thread.currentThread().getName());
    }
}

public class ThreadsGroupOperations {
    public static void main(String arg[]) throws InterruptedException {
        ThreadGroup tg = new ThreadGroup("Threads Group");

        // Op1: Add Threads in tg group
        ThreadOfGroup t1 = new ThreadOfGroup("Thread01", tg);
        ThreadOfGroup t2 = new ThreadOfGroup("Thread02", tg);
        ThreadOfGroup t3 = new ThreadOfGroup("Thread03", tg);

        // Op2: Verify active threads
        System.out.println("Number of active threads: " + tg.activeCount());

        // Op3: Verify maximum priority
        System.out.println("Maximum priority of " + tg.getName() + "group: " + tg.getMaxPriority());

        // Op4: Verify group is daemon thread
        System.out.println(tg.getName() + " is daemon thread group? " + tg.isDaemon());

        // Op5: Change priority
        tg.setMaxPriority(5);
        System.out.println("Maximum priority of " + tg.getName() + " group: " + tg.getMaxPriority());
    }
}