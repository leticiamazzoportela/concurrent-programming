/**
 * StackAtomicReference
 *
 * @author Letícia Mazzo Portela
 * @since Aug/2020
 */
import java.util.concurrent.atomic.AtomicReference;

interface Stack <E> {
	public void push(E e);
	public E pop();
	public E peek();
}

class CustomStack<E> implements Stack<E> {
    private class Node<T> {
        public T element;
        public Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }

    private AtomicReference<Node<E>> head;

    CustomStack() {
        head = new AtomicReference<Node<E>>();
    }

    @Override
    public synchronized void push(E element) {
        Node<E> newHead = new Node<E>(element);
        Node<E> headNode = null;

        do {
            headNode = head.get();
            newHead.next = headNode;
        } while(!head.compareAndSet(headNode, newHead));
    }

    @Override
    public synchronized E pop() {
        Node<E> headNode = head.get();

        do {
            headNode = head.get();

            if (headNode == null)
                return null;
        } while(!head.compareAndSet(headNode, headNode.next));

        return headNode.element;
    }

    public synchronized E peek() {
        Node<E> headNode = head.get();

        if (headNode == null){
            return null;
        }

        return headNode.element;
    }
}

public class StackAtomicReference {
    public static void main(String args[]) {
        final CustomStack<Integer> stack = new CustomStack<Integer>();

        stack.push(1);
        stack.push(3);

        System.out.println("Current Stack: " + stack.peek());

        stack.pop();

        System.out.println("Updated Stack: " + stack.peek());

    }
}
