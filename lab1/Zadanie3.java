import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Zadanie3 {
    static public class CircularDoublyLinkedList {
        int length;
        Element startElement;
        List<Element> elements = new ArrayList<>();

        public CircularDoublyLinkedList() {
            length = 0;
            startElement = null;
        }

        public Element findLastElement(Element startElement) {
            Element findTheLast = startElement;
            while (findTheLast.next != startElement) {
                findTheLast = findTheLast.next;
            }
            return findTheLast;
        }

        public void add(int data) {
            Element element = new Element(data);
            if (startElement == null) {
                startElement = element;
            } else {
                Element findTheLast = findLastElement(startElement);
                findTheLast.next = element;
                element.previous = findTheLast;
                element.next = startElement;
                startElement.previous = element;
            }
            elements.add(element);
            length++;
        }

        public void print() {
            if (startElement == null) {
                System.out.println("List is empty.");
                return;
            }
            Element element = startElement;
            do {
                System.out.print(element.data + " ");
                element = element.next;
            } while (element != startElement);
            System.out.println();
        }
    }

    static public class Element {
        int data;
        Element next;
        Element previous;

        public Element(int data) {
            this.data = data;
            this.next = this;
            this.previous = this;
        }
    }

    public void insert(CircularDoublyLinkedList l, int i) {
        l.add(i);
    }



    public int find(CircularDoublyLinkedList list, int data, boolean searchForward) {
        int numOfComparisons = 0;
        if (list != null) {
            Element element = searchForward ? list.startElement : list.startElement.previous;
            Element stopElement = searchForward ? list.startElement.previous : list.startElement;

            while (element != stopElement) {
                numOfComparisons++;
                if (element.data == data) {
                    return numOfComparisons;
                }
                element = searchForward ? element.next : element.previous;
            }
            numOfComparisons++; // Increment for the last comparison with stop element
        }
        return numOfComparisons;
    }

    public static void main(String[] args) {
        Zadanie3 c = new Zadanie3();
        Random random = new Random();
        CircularDoublyLinkedList list1 = new CircularDoublyLinkedList();
        CircularDoublyLinkedList list2 = new CircularDoublyLinkedList();
        for (int i = 1; i <= 10; i++) {
            int data1 = random.nextInt(90) + 10;
            c.insert(list1, data1);
            int data2 = random.nextInt(90) + 10;
            c.insert(list2, data2);
        }
        list1.print();
        list2.print();


        CircularDoublyLinkedList list3 = new CircularDoublyLinkedList();
        int[] T = new int[10000];
        for (int i = 0; i < 10000; i++) {
            T[i] = random.nextInt(100001);
        }
        for (int i = 0; i < T.length; i++) {
            c.insert(list3, T[i]);
        }
        int sumOfComp1 = 0;
        int sumOfComp2 = 0;

        for (int i = 0; i < 1000; i++) {
            boolean searchForward = random.nextBoolean();
            int randomData1 = T[random.nextInt(T.length) - 1];
            int randomData2 = random.nextInt(100001);
            int numOfComp1 = c.find(list3, randomData1, searchForward);
            int numOfComp2 = c.find(list3, randomData2, searchForward);
            sumOfComp1 = sumOfComp1 + numOfComp1;
            sumOfComp2 = sumOfComp2 + numOfComp2;
        }
        double average1 = (double) sumOfComp1 / 1000;
        double average2 = (double) sumOfComp2 / 1000;

        System.out.println("Average cost of a thousand searches using T: " + average1);
        System.out.println("Average cost of a thousand searches using I: " + average2);
    }
}
