/**
 * ShowPrimeNumbers
 * 
 * @author Letícia Mazzo Portela
 * @since Mar/2020
 */
class PrimeNumber implements Runnable {
    int start, end;

    PrimeNumber(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    private boolean isPrime(final int number) {
        for(int i = 2; i < number; i += 1) {
            if (number % i == 0) return false;
        }

        return true;
    }

    @Override
    public void run() {
        for(int number = this.start; number <= this.end; number +=1) {
            if (isPrime(number)) {
                System.out.println(number + " is prime - Thread: " + Thread.currentThread().getName());
            }
        }
    }
}

class ShowPrimeNumbers extends Thread {
    public static void main(final String[] args) throws InterruptedException {
        int total = 100_000;
        int numberOfThreads = 50;
        int numbersPerThread = total / numberOfThreads;
        
        for(int start = 2; start <= total; start += numbersPerThread) {
            int end = start + numbersPerThread - 1;

            System.out.println("\nNumbers Block: " + start + " - " + end);
            System.out.println("____________\n");

            final Thread t = new Thread(new PrimeNumber(start, end));

            System.out.println("Start Thread " + t.getName());
            t.start();
        }
    }
}