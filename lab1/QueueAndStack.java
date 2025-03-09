import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QueueAndStack {
    //FIFO
    static class Queue<T> {
        private T[] elements;
        int start;
        int end;
        int copacity;

        public Queue( int copacity) {
            // Tworzymy tablicę obiektów i rzutujemy na T[]
            this.copacity = copacity;
            elements = (T[]) new Object[copacity];
            start = 0;
            end = 0;
        }

        public void add(T element) {
            if (end >= this.copacity) {
                throw new IllegalStateException("Kolejka jest przepełniona.");
            }
            elements[end] = element;
            end++;
        }

        public T pick() {
            if (start == end) {
                throw new IllegalStateException("Kolejka jest pusta.");
            }
            T result = elements[start];
            start++;
            return result;
        }

        public void print() {
            for (int i = 0; i < 50; i++) {
                try {
                    T element = pick();
                    System.out.print(" " + element + " ");
                } catch (IllegalStateException e) {
                    System.out.println("E: " + e.getMessage());
                    break;
                }
            }
        }
    }

    // LIFO
    static class Stack<T> {
        private T[] elements;
        private int top;
        private int capacity;

        public Stack(int capacity) {
            this.capacity = capacity;
            elements = (T[]) new Object[capacity];
            top = 0;
        }

        public void push(T element) {
            if (top >= capacity) {
                throw new IllegalStateException("Stos jest przepełniony.");
            }
            elements[top] = element;
            top++;
        }

        public T pop() {
            if (top == 0) {
                throw new IllegalStateException("Stos jest pusty.");
            }
            top--;
            return elements[top];
        }
        public void print(){
            for (int i = 0; i < 50; i++) {
                try {
                    T element = pop();
                    System.out.print(" " + element+" ");
                } catch (IllegalStateException e) {
                    System.out.println("E: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        int copacity = 50;
        Queue<Integer> queue = new Queue<>(50);
        Stack<Integer> stack = new Stack<>(50);
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
