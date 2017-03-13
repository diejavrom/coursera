package deques;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] items;
    private int head = 0, tail = 0;

    // construct an empty deque
    public Deque() {
        this.items = (Item[]) new Object[2];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return tail - head;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("cannot add a null item");
        }
        if (head == 0) {
            resize(2 * items.length);
        }
        items[head - 1] = item;
        head--;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("cannot add a null item");
        }
        if (tail == items.length) {
            resize(2 * items.length);
        }
        items[tail] = item;
        tail++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("the deque is empty");
        }
        Item first = items[head];
        items[head] = null;
        head++;
        return first;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("the deque is empty");
        }
        Item last = items[tail - 1];
        items[tail-1] = null;
        tail--;
        return last;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int from = items.length / 2;
        for (int i = 0; i < tail - head; i++) {
            copy[i + from] = items[head + i];
        }
//        if(head == 0) {
            tail += from;
//        } else {
//            tail++;
//        }
        head += from;
        items = copy;
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
//        deque.addFirst(1); // 1
//        deque.addFirst(2); // 2 1
//        deque.addLast(3); // 2 1 3
//        deque.addLast(4); // 2 1 3 4
//        deque.addFirst(5); // 5 2 1 3 4
//        deque.addFirst(6); // 6 5 2 1 3 4
//        deque.addLast(7); // 6 5 2 1 3 4 7
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeLast());
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeLast());
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeLast());
//        for (Integer n : deque) {
//            System.out.println(n);
//        }
        
//        deque.addLast(0);
//        deque.size();
//        deque.size();
//        deque.addLast(3);
//        deque.addLast(4);
//        deque.addLast(5);
//        System.out.println(deque.size());
//        deque.addFirst(6);
//        System.out.println(deque.size());

//        System.out.println(deque.removeFirst());     //==> 0
//        deque.isEmpty();
//        deque.isEmpty();
//        deque.addLast(4);
//        deque.addLast(5);
//        System.out.println(deque.removeFirst());     //==> null       pero debe ser 4
        
        deque.addLast(0);
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        System.out.println(deque.removeFirst());        
    }

    private class ListIterator implements Iterator<Item> {

        private int current;

        public ListIterator() {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current < tail;
        }

        @Override
        public Item next() {
            if (current >= tail) {
                throw new NoSuchElementException("the iterator has reached the end");
            }
            return items[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

}