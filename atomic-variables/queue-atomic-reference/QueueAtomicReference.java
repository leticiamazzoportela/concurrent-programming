/**
 * QueueAtomicReference
 *
 * @author Letícia Mazzo Portela
 * @since Aug/2020
 */
import java.util.concurrent.atomic.AtomicReference;

class CustomQueue<E> {
    private class Node<T> {
        public T element;
        public Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }

    private final AtomicReference<Node<E>> head, tail;

    public CustomQueue() {
        Node<E> auxNode = new Node<E>(null);
        head = new AtomicReference<Node<E>>(auxNode);
        tail = new AtomicReference<Node<E>>(auxNode);
    }

    public void add(E element) {
        if (element == null) throw new NullPointerException();

        Node<E> node = new Node<E>(element);
        Node<E> prevTail = tail.getAndSet(node);
        prevTail.next = node;
    }

    public E get() {
        Node<E> newHead, next;

        do {
            newHead = head.get();
            next = newHead.next;

            if (next == null) return null;

        } while (!head.compareAndSet(newHead, next));

        E element = next.element;
        next.element = null;

        return element;
    }
}

public class QueueAtomicReference {
    public static void main(String args[]) {
        final CustomQueue<Integer> queue = new CustomQueue<Integer>();

        queue.add(1);
        queue.add(3);
        queue.add(5);

        System.out.println("Current queue: " + queue.get());
    }
}
