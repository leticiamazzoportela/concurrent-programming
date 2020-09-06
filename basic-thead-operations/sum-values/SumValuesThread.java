import java.util.*;

/**
 * SumValuesThread
 * 
 * @author Letícia Mazzo Portela
 * @since Mar/2020
 */
public class SumValuesThread extends Thread {
    public static int sum = 0;

    @Override
    public void run() {
        System.out.println("Quantos números deseja informar? ");
        Scanner value = new Scanner(System.in);
        int qt = value.nextInt();

        int[] values = new int[qt];

        for(int i = 0; i < qt; i++) {
            System.out.println("Informe o " + (i+1) + "º número: ");
            values[i] = value.nextInt();
        }

        sum = Arrays.stream(values).reduce(0, (x, y) -> x + y);
    }

    public static void main(final String[] args) throws InterruptedException {
        System.out.println("...Executing Thread...");
        final Thread t = new Thread(new SumValuesThread());
        t.start();
        t.join();

        System.out.println("...Thread finished...");
        System.out.print("Soma: " + sum + "\n");

    }
}