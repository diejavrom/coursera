package deques;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int last;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.items = (Item[]) new Object[2];
        this.last = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return last == 0;
    }

    // return the number of items on the queue
    public int size() {
        return last;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("cannot add a null item");
        }
        if (last == items.length - 1) {
            resize(2 * items.length);
        }
        items[last] = item;
        last++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("the queue is empty");
        }
        int i = StdRandom.uniform(last);
        Item r = items[i];
        items[i] = items[last - 1];
        last--;
        return r;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("the queue is empty");
        }
        return items[StdRandom.uniform(last)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < last; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    private class RandomQueueIterator implements Iterator<Item> {

        private Item[] internalItems;
        private int current;

        public RandomQueueIterator() {
            this.internalItems = (Item[]) new Object[last];
            for (int i = 0; i < last; i++) {
                internalItems[i] = items[i];
            }
            StdRandom.shuffle(this.internalItems);
            this.current = 0;
        }

        @Override
        public boolean hasNext() {
            return current >= 0 && current < internalItems.length;
        }

        @Override
        public Item next() {
            if (current >= internalItems.length) {
                throw new NoSuchElementException("the iterator has reached the end");
            }
            Item item = internalItems[current];
            current++;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> r = new RandomizedQueue<>();
        r.enqueue(1);
        r.enqueue(2);
        r.enqueue(3);
        r.enqueue(4);
        r.enqueue(5);
        r.dequeue();
        r.enqueue(6);
        r.dequeue();
        r.dequeue();
        r.enqueue(6);
        for (Integer n : r) {
            System.out.println(n);
        }
    }

}