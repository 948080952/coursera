
import java.util.Iterator;
import edu.princeton.cs.algs4.*;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private Item[] queue;

    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        validate(item);
        if (size == queue.length) {
            Item[] newQueue = (Item[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                newQueue[i] = queue[i];
            }
            queue = newQueue;
        }
        queue[size] = item;
        size++;
    }

    public Item dequeue() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        int random = StdRandom.uniform(size);
        size--;
        Item item = queue[random];
        queue[random] = queue[size];
        queue[size] = null;



        if (size == queue.length / 4 && size != 0) {
            Item[] newQueue = (Item[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                newQueue[i] = queue[i];
            }
            queue = newQueue;
        }

        return item;

    }

    public Item sample() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        int random = StdRandom.uniform(size);
        return queue[random];

    }

    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    private Item getItemAt(int index) {
        return queue[index];
    }

    private void validate(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
    }

    private class MyIterator implements Iterator<Item> {
        private DPRandomList list;

        public MyIterator() {
            list = new DPRandomList(size);
        }

        public boolean hasNext() {

            return list.hasNext();

        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            int random = list.next();
            Item item = getItemAt(random);
            return item;
        }

        public void remove() {

            throw new java.lang.UnsupportedOperationException();

        }
    }


    private class DPRandomList {

        int[] map;
        int count;

        private DPRandomList(int count) {

            this.map = new int[count];
            this.count = count;

            for (int i = 0; i < count; i++) {
                map[i] = i;
            }
        }

        private boolean hasNext() {
            if (count > 0) {
                return true;
            }
            return false;
        }

        private int next() {
            if (count == 0) {
                throw new java.util.NoSuchElementException();
            }
            int random = StdRandom.uniform(count);
            count--;
            int value = map[random];
            map[random] = map[count];
            return value;
        }

    }


    public static void main(String[] args) {
        
    }
}