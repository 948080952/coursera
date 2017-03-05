
import java.util.Iterator;
import edu.princeton.cs.algs4.*;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item item;
        private Node next;
        private Node pre;
    }

    private Node first;
    private Node last;
    private int size;

    public Deque() {

       this.size = 0;
       this.first = null;
       this.last = null;
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

	public void addFirst(Item item) {

        validate(item);

        Node newNode = new Node();
        newNode.item = item;
        newNode.pre = null;
        Node oldNode = first;
        newNode.next = oldNode;
        if (oldNode != null) {
            oldNode.pre = newNode;
        } else {
            last = newNode;
        }

        first = newNode;

        size++;

    }

	public void addLast(Item item) {

        validate(item);

        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;
        Node oldNode = last;
        newNode.pre = oldNode;
        if (oldNode != null) {
            oldNode.next = newNode;
        } else {
            first = newNode;
        }

        last = newNode;

        size++;

    }
		
	public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        Node oldNode = first;
        first = first.next;

        if (first == null) {
            last = null;
        } else {
            first.pre = null;
        }
        size--;
        return oldNode.item;
	}

	public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        Node oldNode = last;
        last = last.pre;

        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        size--;
        return oldNode.item;

	}

	public Iterator<Item> iterator() {
        return new MyIterator();
    }
		
	private class MyIterator implements Iterator<Item> {

        private Node current = first;
				
		public boolean hasNext() {
		    return current != null;
		}

		public Item next() {
		    if (!hasNext()) {
		        throw new java.util.NoSuchElementException();
            }
		    Item item = current.item;
		    current = current.next;
		    return item;
		}

		public void remove() {
						
			throw new java.lang.UnsupportedOperationException();

		}

	}

	private void validate(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
    }

    public static void main(String[] args) {

        Deque<String> stringDeque = new Deque<String>();

        for (int i = 0; i < 5; i++) {
            String tmp = "first" + i;
            stringDeque.addFirst(tmp);
        }

        for (int i = 0; i < 5; i++) {
            String tmp = "last" + i;
            stringDeque.addLast(tmp);
        }

        for (String string : stringDeque) {
            System.out.println(string);
        }

        for (int i = 0; i < 10; i++) {
            stringDeque.removeFirst();
        }

        for (String string : stringDeque) {
            System.out.println(string);
        }

    }

} 
