import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Zadanie2 {
    static public class CircularLinkedList {
        int length;
        Element startElement;


        public CircularLinkedList() {
            length = 0;
            startElement = null;
        }

        public Element FindLastElement(Element startElement) {
            Element FindTheLast = startElement;
            while (FindTheLast.next != startElement) {
                FindTheLast = FindTheLast.next;
            }
            return FindTheLast;
        }

        public void add(int data) {
            Element element = new Element(data);
            if (startElement == null) {
                startElement = element;
            } else {
                Element FindTheLast = FindLastElement(startElement);
                FindTheLast.next = element;
                element.next = startElement;
            }
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

        public Element(int data) {
            this.data = data;
            this.next = this;
        }

    }

    public void insert(CircularLinkedList l, int i) {
        l.add(i);
    }

    public CircularLinkedList merge(CircularLinkedList l1, CircularLinkedList l2) {
        if (l1.startElement == null && l2.startElement != null) {
            l1.startElement = l2.startElement;
        } else {
            Element FindTheLast = l1.FindLastElement(l1.startElement);
            FindTheLast.next = l2.startElement;
            FindTheLast = l2.FindLastElement(l2.startElement);
            FindTheLast.next = l1.startElement;
            l1.length += l2.length;

        }
        return l1;
    }

    public int find(CircularLinkedList list, int data) {
        int numOfComparisons = 0;
        if (list != null) {
            Element element = list.startElement;
            do {
                numOfComparisons++;
                if (element.data == data) {
                   return numOfComparisons;
                }
                element = element.next;
            } while (element != list.startElement);
        }
        return numOfComparisons;
    }

    public static void main(String[] args) {
        Zadanie2 c = new Zadanie2();
        Random random = new Random();
        CircularLinkedList list1 = new CircularLinkedList();
        CircularLinkedList list2 = new CircularLinkedList();
        for (int i = 1; i <= 10; i++) {
            int data1 = random.nextInt(90) + 10;
            c.insert(list1, data1);
            int data2 = random.nextInt(90) + 10;
            c.insert(list2, data2);

        }
        list1.print();
        list2.print();
        CircularLinkedList mergedlist = c.merge(list1, list2);
        mergedlist.print();

        CircularLinkedList list3 = new CircularLinkedList();
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
            int randomData1 = T[random.nextInt(T.length)-1];
            int randomData2 = random.nextInt(100001);
            int numOfComp1 = c.find(list3, randomData1);
            int numOfComp2 = c.find(list3, randomData2);
            sumOfComp1 = sumOfComp1+numOfComp1;
            sumOfComp2 = sumOfComp2+numOfComp2;

        }
        double average1 = (double) sumOfComp1/1000;
        double average2 = (double) sumOfComp2/1000;


        System.out.println("Average cost of a thousand searches using T: " + average1);
        System.out.println("Average cost of a thousand searches using I: " + average2);


    }
}
