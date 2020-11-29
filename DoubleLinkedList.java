
/*
 This is the Doubly Linked List class that is made from scratch
 without using the built in java class. This class contains nested
 classes inside that code for nodes and the iterator. This class
 has functions for adding, getting, setting, and removing elements
 from the list. It also keeps track of the index and the size.
*/

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<T> implements Iterable<T> {

	private int n; // size of the list
	private Node head; // head node
	private Node tail; // tail node

	// default constructor
	public DoubleLinkedList() {
		head = new Node();
		tail = new Node();
		// only two nodes to start out
		head.next = tail;
		tail.previous = head;
		n = 0;
	}

	// returns size of the list
	public int size() {
		return n;
	}

	// nested node class
	private class Node {
		private T data; // data at a current node
		private Node next; // next node
		private Node previous; // previous node
	}

	// adding an element to the list
	public void add(T data) {
		Node last = tail.previous;
		Node x = new Node();
		x.data = data;
		x.next = tail;
		x.previous = last;
		tail.previous = x;
		last.next = x;
		n++; // increments size by 1
	}

	// creates an iterator for a given list
	public DoublyLinkedListIterator iterator() {
		return new DoublyLinkedListIterator();
	}

	// nested iterator class
	public class DoublyLinkedListIterator implements ListIterator<T> {

		private Node current = head.next; // current node
		private Node lastUsed = null; // last node used
		private int index = 0; // current index

		// checking if list has any more elements after
		public boolean hasNext() {
			return index < n;
		}

		// checking if list has any previous elements
		public boolean hasPrevious() {
			return index > 0;
		}

		// returns previous index
		public int previousIndex() {
			return index - 1;
		}

		// returns next index
		public int nextIndex() {
			return index;
		}

		// returns the next element in the list
		public T next() {
			if (!hasNext()) { // checks if there is a next
				throw new NoSuchElementException();
			}
			// shift nodes up and returns data in current
			lastUsed = current;
			T data = current.data;
			current = current.next;
			index++;
			return data;
		}

		// returns the previous element in the list
		public T previous() {
			if (!hasPrevious()) { // checks if there is a previous
				throw new NoSuchElementException();
			}
			// shifts nodes down and returns data in previous
			current = current.previous;
			index--;
			lastUsed = current;
			return current.data;
		}

		// sets a value at the current position in the list
		public void set(T data) {
			if (lastUsed == null) {
				throw new IllegalStateException();
			}
			lastUsed.data = data;
		}

		// removes the current position in the list
		public void remove() {
			if (lastUsed == null) {
				throw new IllegalStateException();
			}
			// temp nodes for readability
			Node x = lastUsed.previous;
			Node y = lastUsed.next;
			x.next = y;
			y.previous = x;
			n--; // decrement size
			if (current == lastUsed) {
				current = y;
			} else {
				index--; // decrement index
			}
			lastUsed = null;

		}

		// adds an element to the list
		public void add(T data) {
			// temp nodes for readability
			Node x = current.previous;
			Node y = new Node();
			Node z = current;
			y.data = data;
			x.next = y;
			y.next = z;
			z.previous = y;
			y.previous = x;
			// increment index and size of list
			n++;
			index++;
			lastUsed = null;
		}

		// gets a value from the list given an index
		public T get(int i) {
			current = head;
			int count = -1;
			// loop through entire list
			while (current != null) {
				// return value at desired index
				if (count == i) {
					return current.data;
				}
				count++;
				current = current.next; // move to next node
			}
			return null;
		}

	}
}