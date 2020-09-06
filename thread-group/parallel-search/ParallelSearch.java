import java.util.Scanner;

/**
 * ParallelSearch
 * 
 * @author Letícia Mazzo Portela
 * @since Mar/2020
 */
class Search implements Runnable {
    int start, end, target, index;
    int[] numbers;

    public Search(final int start, final int end, final int[] numbers, final int target, final int index) {
        super();
        if (end < start) {
            throw new IllegalStateException();
        }
        this.start = start;
        this.end = end;
        this.numbers = numbers;
        this.target = target;
        this.index = index;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            if (target == numbers[i]) {
                this.index = i;
                System.out.println("Number " + 
                target + 
                " found at position " + 
                i + 
                " by thread " + 
                Thread.currentThread().getName());
            }
        }

        if(this.index == -1) {
            System.out.println("Number " + 
            target + 
            " not found by thread " + 
            Thread.currentThread().getName());
        }
    }
}

class SearchController extends Thread {
    int target, numberOfThreads;
    int[] numbers;
    int index = -1;

    SearchController(final int target, final int numberOfThreads, final int[] numbers) {
        this.target = target;
        this.numbers = numbers;
        this.numberOfThreads = numberOfThreads;
    }

    public void doParallelSearch() {
        final int partitionSize = numbers.length / numberOfThreads;
        final Thread[] threads = new Thread[numberOfThreads];
        int start = 0, end = 0;

        for (int n = 0; n < numberOfThreads; n += 1) {
            end = start + partitionSize;

            if (n == numberOfThreads || end > numbers.length) {
                end = numbers.length;
            }

            final Search s = new Search(start, end, numbers, target, index);
            threads[n] = new Thread(s);

            System.out.println("Start Thread " + threads[n].getName());
            threads[n].start();

            start = end;
        }
    }
}

class ParallelSearch {
    public static void main(final String[] args) {
        final Scanner input = new Scanner(System.in);

        System.out.println("Qual a quantidade de itens?");
        final int[] numbers = new int[input.nextInt()];

        System.out.println("E o número de threads?");
        final int numberOfThreads = input.nextInt();

        System.out.println("Finalmente, qual número deseja procurar?");
        final int target = input.nextInt();

        final int min = 1;
        final int max = 10;

        System.out.println("Numbers: ");
        for (int i = 0; i < numbers.length; i += 1) {
            numbers[i] = (int) (Math.random() * (max - min + 1) + min);
            System.out.print(numbers[i] + " ");
        }
        System.out.println("\n_______\n");

        final SearchController sc = new SearchController(target, numberOfThreads, numbers);
        sc.doParallelSearch();
    }
}