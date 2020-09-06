/**
 * SequentialNumbersGenerator
 *
 * @author Letícia Mazzo Portela
 * @since Aug/2020
 */
import java.util.concurrent.atomic.AtomicLong;

class SequenceGenerator {
    private AtomicLong value = new AtomicLong(0);

    public void increment() {
        long currentValue = value.get();
        long newValue = currentValue + 1;

        if (value.compareAndSet(currentValue, newValue)) {
            return;
        }
    }

    public long updatedValue() {
        return value.get();
    }
}

public class SequentialNumbersGenerator {
    public static void main(String args[]) {
        final SequenceGenerator generator  = new SequenceGenerator();
        long value = generator.updatedValue();

        while (value < 10) {
            generator.increment();
            value = generator.updatedValue();
            System.out.println("Value: " + value);
        }

    }
}
