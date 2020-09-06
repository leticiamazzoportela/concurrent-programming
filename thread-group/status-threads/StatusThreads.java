/**
 * StatusThreads
 * 
 * @author Letícia Mazzo Portela
 * @since Mar/2020
 */
class ThreadOfGroup extends Thread {
    ThreadOfGroup(String threadName, ThreadGroup groupName) throws InterruptedException {
        super(groupName, threadName);

        System.out.println("Start Thread " + threadName);
        start();

        if (threadName == "Thread02") {
            join();
            System.out.println("State of Thread " + threadName + ": " + getState());
        }
    }

    @Override
    public void run() {
        int i = 0;

        System.out.println("State of Thread " + Thread.currentThread().getName() + " before sleep: " + Thread.currentThread().getState());
        while(i < 5) {
            try {
                Thread.sleep(5000);
                System.out.println("State of Thread " + Thread.currentThread().getName() + " in sleep: " + Thread.currentThread().getState());
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            i += 1;
        }

        System.out.println("State of Thread " + Thread.currentThread().getName() + " before finish: " + Thread.currentThread().getState());
        System.out.println("Finish Thread " + Thread.currentThread().getName());
        System.out.println("State of Thread " + Thread.currentThread().getName() + " after finish: " + Thread.currentThread().getState());
    }
}

public class StatusThreads {
    public static void main(String arg[]) throws InterruptedException {
        ThreadGroup tg = new ThreadGroup("Threads Group with States");
        ThreadOfGroup t1 = new ThreadOfGroup("Thread01", tg);
        ThreadOfGroup t2 = new ThreadOfGroup("Thread02", tg);
        ThreadOfGroup t3 = new ThreadOfGroup("Thread03", tg);
    }
}