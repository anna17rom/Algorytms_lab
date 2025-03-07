import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QueueAndStack {
    //FIFO
    static class Queue<T> {
        private List<T> elemens = new ArrayList<>();

        public void add(T element) {
            elemens.add(element);
        }

        public T pick() {
            if (elemens.isEmpty()) {
                throw new IllegalStateException("There is no elements in the queue");
            }
            return elemens.remove(0);
        }

        public void print() {
            for (int i = 0; i < 50; i++) {
                try {
                    int element = (int) this.pick();
                    System.out.print(" " + element + " ");
                } catch (IllegalStateException e) {
                    System.out.println("E: " + e.getMessage());
                }
            }
        }
    }

    // LIFO
    static class Stack<T> {
        private List<T> elements= new ArrayList<>();

        public void push(T element) {
            elements.add(element);
        }

        public T pop() {
            if (elements.isEmpty()) {
                throw new IllegalStateException("Stos jest pusty");
            }
            return elements.remove(elements.size() - 1);
        }
        public void print(){
            for (int i = 0; i < 50; i++) {
                try {
                    int element = (int) this.pop();
                    System.out.print(" " + element+" ");
                } catch (IllegalStateException e) {
                    System.out.println("E: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        Stack<Integer> stack = new Stack<>();
        Random random = new Random();

        for (int i = 1; i <= 50; i++) {
            int element =  random.nextInt(21) - 10;
            queue.add(element);
            stack.push(element);
        }
        System.out.println("\nQueue:");
        queue.print();
        System.out.println("\nStack:");
        stack.print();
    }
}
