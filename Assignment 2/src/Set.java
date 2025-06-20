/**
 * COMPSCI 1027B Assignment 2
 * Set.java
 * Due: February 27, 2023
 * 
 * @author Matthew Molloy
 * 
 * Creates a generic type T singly linked list
 */
public class Set<T> {
	private LinearNode<T> setStart;
	
	public Set() {
		setStart = null;
	}
	/**
	 * Adds a new node to the front of the set
	 * 
	 * @param element of generic type
	 */
	public void add(T element) {
		LinearNode<T> newNode = new LinearNode(element);
		if (setStart == null) setStart = newNode;
		
		else {
			/*Make a placeholder node for SetStart, since this will be
			* overwritten by the newNode
			*/
			LinearNode<T> shiftNodeRight = setStart;
			setStart = newNode;
			setStart.setNext(shiftNodeRight);
		}
	}
	
	/**
	 * 
	 * @return length of the set
	 */
	public int getLength(){
		// Counter tracks list length, starts at 1 since there is always setStart Node
		int counter = 0;
		LinearNode<T> currentNode = setStart;
		// cannot getNext from an empty set, so while loop check for empty set
		if (setStart == null) return counter;
		while(currentNode.getNext() != null) {
			// Increment counter and point currentNode to the next node in list
			counter++;
			currentNode = currentNode.getNext();
		}
		counter++;
		return counter;
	}
	
	/**
	 * 
	 * @param integer i 
	 * @return the i'th element in the linked list
	 */
	public T getElement(int i) {
		int counter = 0;
		LinearNode<T> currentNode = setStart;
		while (counter != i) {
			counter++;
			currentNode = currentNode.getNext();
		}
		return currentNode.getElement();
	}

	/**
	 * 
	 * @param element of type T
	 * @return true if given element in found in the linked list
	 */
	public boolean contains(T element) {
		LinearNode<T> currentNode = setStart;
		while (currentNode.getNext() != null) {
			if (currentNode.getElement() == element) return true;
			currentNode = currentNode.getNext();
		}
		return false;
	}
	/**
	 * @return String of each element in the linked list, separated by a space
	 */
	public String toString() {
		String s = "";
		LinearNode<T> currentNode = setStart;
		while (currentNode.getNext() != null) {
			s += currentNode.getElement() + " ";
			currentNode = currentNode.getNext();
		}
		// Add this since while loop skips over last node
		s += currentNode.getElement();
		return s;
	}
}
