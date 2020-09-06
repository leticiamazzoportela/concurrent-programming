import java.util.Scanner;

/**
 * SearchValue
 *
 * @author Letícia Mazzo Portela
 * @since Aug/2020
 */
class Search implements Runnable {
    int start, end, index;
    String target;
    String[] pokemons;
    volatile boolean pokemonFound = false;

    public Search(
        final int start,
        final int end,
        final String[] pokemons,
        final String target,
        final int index
    ) {
        super();
        if (end < start) {
            throw new IllegalStateException();
        }
        this.start = start;
        this.end = end;
        this.pokemons = pokemons;
        this.target = target;
        this.index = index;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            if (target.equals(pokemons[i])) {
                this.index = i;
                pokemonFound = true;

                System.out.println("Pokémon " +
                target +
                " found at position " +
                i +
                " by thread " +
                Thread.currentThread().getName());
            }
        }
        System.out.println(Thread.currentThread().getThreadGroup().getName());
        if (pokemonFound) {
            final ThreadGroup tg = Thread.currentThread().getThreadGroup();

            System.out.println("Interrrupting All Threads of the group: " +
                Thread.currentThread().getThreadGroup().getName());

            tg.interrupt();
        }
    }
}

class SearchController extends Thread {
    String target;
    String[] pokemons;
    int index = -1;

    SearchController(final String target, final String[] pokemons) {
        this.target = target;
        this.pokemons = pokemons;
    }

    public void doParallelSearch() {
        final int partitionSize = pokemons.length / 2;
        ThreadGroup tg = new ThreadGroup("Pokémon Thread Group");
        final Thread[] threads = new Thread[2];
        int start = 0, end = 0;

        for (int n = 0; n < 2; n += 1) {
            end = start + partitionSize;

            if (n == 2 || end > pokemons.length) {
                end = pokemons.length;
            }

            final Search s = new Search(start, end, pokemons, target, index);
            threads[n] = new Thread(tg, s, "Thread" + "-" + n);
            System.out.println("Start Thread " + threads[n].getName());
            threads[n].start();

            start = end;
        }
    }
}

class SearchValue {
    public static void main(final String[] args) {
        final Scanner input = new Scanner(System.in);
        final String[] pokemons = {"Pikachu", "Charmander", "Squirtle", "Bulbasaur", "Pidgeotto", "Psyduck"};

        System.out.println("Type the name of the Pokémon that you want: ");
        final String target = input.nextLine();

        final SearchController sc = new SearchController(target, pokemons);
        sc.doParallelSearch();
    }
}
